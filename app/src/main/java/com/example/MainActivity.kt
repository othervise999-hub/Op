package com.example

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.billing.BillingManager
import com.example.data.AppDatabase
import com.example.data.ProjectEntity
import com.example.data.ProjectRepository
import com.example.engine.VideoProcessingEngine
import com.example.model.EnhancementSettings
import com.example.model.ExportFormat
import com.example.model.FilterType
import com.example.model.LanguageDubbingSettings
import com.example.model.QualityOption
import com.example.model.ShortClipSettings
import com.example.model.VideoMetadata
import com.example.ui.components.ProcessingProgressDialog
import com.example.ui.screens.ExportScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProcessingConfigScreen
import com.example.ui.theme.MyApplicationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class Screen {
    object Home : Screen()
    data class Config(val metadata: VideoMetadata) : Screen()
    data class Export(
        val metadata: VideoMetadata,
        val quality: QualityOption,
        val filter: FilterType,
        val settings: EnhancementSettings,
        val exportFormat: ExportFormat,
        val outputFilePath: String?,
        val dubbingSettings: LanguageDubbingSettings? = null,
        val shortClipSettings: ShortClipSettings? = null
    ) : Screen()
}

class MainActivity : ComponentActivity() {

    private lateinit var database: AppDatabase
    private lateinit var repository: ProjectRepository
    private lateinit var billingManager: BillingManager
    private lateinit var processingEngine: VideoProcessingEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        database = AppDatabase.getInstance(this)
        repository = ProjectRepository(database.projectDao())
        billingManager = BillingManager.getInstance(this)
        processingEngine = VideoProcessingEngine(this)

        setContent {
            MyApplicationTheme {
                UltraVideoApp(
                    repository = repository,
                    billingManager = billingManager,
                    processingEngine = processingEngine
                )
            }
        }
    }
}

@Composable
fun UltraVideoApp(
    repository: ProjectRepository,
    billingManager: BillingManager,
    processingEngine: VideoProcessingEngine,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    val processingState by processingEngine.processingState.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = currentScreen,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "screen_transition"
        ) { targetScreen ->
            when (targetScreen) {
                is Screen.Home -> {
                    HomeScreen(
                        repository = repository,
                        billingManager = billingManager,
                        processingEngine = processingEngine,
                        onSelectVideoUri = { uri, isDemo, demoRes ->
                            coroutineScope.launch {
                                val meta = processingEngine.extractMetadata(uri, isDemo, demoRes)
                                currentScreen = Screen.Config(meta)
                            }
                        },
                        onOpenProject = { project ->
                            val isPhoto = project.sourceUri.contains(".jpg", ignoreCase = true) ||
                                project.sourceUri.contains(".jpeg", ignoreCase = true) ||
                                project.sourceUri.contains(".png", ignoreCase = true) ||
                                project.sourceUri.contains(".webp", ignoreCase = true) ||
                                project.title.contains(".jpg", ignoreCase = true) ||
                                project.title.contains(".png", ignoreCase = true) ||
                                project.thumbnailResId == R.drawable.sample_portrait ||
                                project.thumbnailResId == R.drawable.sample_scenic

                            val meta = VideoMetadata(
                                uri = Uri.parse(project.sourceUri),
                                fileName = project.title,
                                width = if (isPhoto) 3000 else 1920,
                                height = if (isPhoto) 4000 else 1080,
                                fps = if (isPhoto) 0 else 30,
                                durationMs = project.durationMs,
                                fileSizeBytes = 24000000L,
                                aspectRatio = if (isPhoto) "3:4" else "16:9",
                                isDemo = project.thumbnailResId != null,
                                thumbnailDrawableId = project.thumbnailResId,
                                isPhoto = isPhoto
                            )
                            val qOption = QualityOption.values().find { it.title == project.targetQuality }
                                ?: QualityOption.UHD_4K
                            val fType = FilterType.values().find { it.title == project.filterType }
                                ?: FilterType.CINEMATIC
                            val settings = EnhancementSettings(
                                superResolution = project.superResolution,
                                sharpness = project.sharpness,
                                detailRecovery = project.detailRecovery,
                                noiseReduction = project.noiseReduction
                            )
                            currentScreen = Screen.Export(
                                metadata = meta,
                                quality = qOption,
                                filter = fType,
                                settings = settings,
                                exportFormat = if (isPhoto) ExportFormat.JPG_HIGH else ExportFormat.MP4_H264,
                                outputFilePath = project.outputPath
                            )
                        }
                    )
                }

                is Screen.Config -> {
                    ProcessingConfigScreen(
                        metadata = targetScreen.metadata,
                        processingEngine = processingEngine,
                        billingManager = billingManager,
                        onBack = { currentScreen = Screen.Home },
                        onStartEnhancement = { quality, filter, settings, exportFormat, hasWatermark, dubbingSettings, shortClipSettings ->
                            processingEngine.startEnhancement(
                                metadata = targetScreen.metadata,
                                quality = quality,
                                filter = filter,
                                settings = settings,
                                exportFormat = exportFormat,
                                hasWatermark = hasWatermark,
                                dubbingSettings = dubbingSettings,
                                shortClipSettings = shortClipSettings
                            ) { outputFile ->
                                val finalQualityLabel = if (shortClipSettings?.isShortClipMode == true) "6K Short Clip" else quality.title
                                val finalOutputRes = if (shortClipSettings?.isShortClipMode == true) "3456 × 6144 (6K)" else if (quality == QualityOption.ORIGINAL) targetScreen.metadata.resolutionText() else quality.resolutionLabel

                                // On complete, insert into Room Database
                                coroutineScope.launch(Dispatchers.IO) {
                                    val project = ProjectEntity(
                                        title = if (shortClipSettings?.isShortClipMode == true) "Short - ${targetScreen.metadata.fileName}" else targetScreen.metadata.fileName,
                                        sourceUri = targetScreen.metadata.uri.toString(),
                                        thumbnailResId = targetScreen.metadata.thumbnailDrawableId,
                                        durationMs = if (shortClipSettings?.isShortClipMode == true) (shortClipSettings.customEndMs - shortClipSettings.customStartMs).coerceAtLeast(3000L) else targetScreen.metadata.durationMs,
                                        originalResolution = targetScreen.metadata.resolutionText(),
                                        targetQuality = finalQualityLabel,
                                        filterType = filter.title,
                                        superResolution = settings.superResolution,
                                        sharpness = settings.sharpness,
                                        detailRecovery = settings.detailRecovery,
                                        noiseReduction = settings.noiseReduction,
                                        outputResolution = finalOutputRes,
                                        outputSizeBytes = outputFile.length(),
                                        outputPath = outputFile.absolutePath,
                                        isCloudProcessed = quality.isCloud
                                    )
                                    repository.insert(project)
                                }

                                currentScreen = Screen.Export(
                                    metadata = targetScreen.metadata,
                                    quality = if (shortClipSettings?.isShortClipMode == true) QualityOption.UHD_6K else quality,
                                    filter = filter,
                                    settings = settings,
                                    exportFormat = exportFormat,
                                    outputFilePath = outputFile.absolutePath,
                                    dubbingSettings = dubbingSettings,
                                    shortClipSettings = shortClipSettings
                                )
                            }
                        }
                    )
                }

                is Screen.Export -> {
                    ExportScreen(
                        metadata = targetScreen.metadata,
                        quality = targetScreen.quality,
                        filter = targetScreen.filter,
                        settings = targetScreen.settings,
                        exportFormat = targetScreen.exportFormat,
                        outputFilePath = targetScreen.outputFilePath,
                        processingEngine = processingEngine,
                        onEnhanceAnother = { currentScreen = Screen.Home },
                        dubbingSettings = targetScreen.dubbingSettings,
                        shortClipSettings = targetScreen.shortClipSettings
                    )
                }
            }
        }

        // Active Multi-Stage Processing Modal
        if (processingState.isProcessing) {
            ProcessingProgressDialog(
                processingState = processingState,
                onPause = { processingEngine.pauseProcessing() },
                onResume = { processingEngine.resumeProcessing() },
                onCancel = { processingEngine.cancelProcessing() }
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "UltraVideo AI - Remastering $name", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme { Greeting("Android") }
}
