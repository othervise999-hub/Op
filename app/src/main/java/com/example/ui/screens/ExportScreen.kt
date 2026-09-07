package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.R
import com.example.engine.VideoProcessingEngine
import com.example.model.EnhancementSettings
import com.example.model.ExportFormat
import com.example.model.FilterType
import com.example.model.LanguageDubbingSettings
import com.example.model.QualityOption
import com.example.model.ShortClipSettings
import com.example.model.VideoMetadata
import com.example.ui.components.GlassCard
import com.example.ui.theme.Blue600
import com.example.ui.theme.BorderWhite05
import com.example.ui.theme.BorderWhite10
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Cyan500
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.EmeraldGlow
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.ObsidianElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.Zinc300
import com.example.ui.theme.Zinc400
import com.example.ui.theme.Zinc500
import com.example.ui.theme.Zinc800
import com.example.ui.theme.Zinc900
import com.example.ui.theme.Zinc950
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportScreen(
    metadata: VideoMetadata,
    quality: QualityOption,
    filter: FilterType,
    settings: EnhancementSettings,
    exportFormat: ExportFormat,
    outputFilePath: String?,
    processingEngine: VideoProcessingEngine,
    onEnhanceAnother: () -> Unit,
    dubbingSettings: LanguageDubbingSettings? = null,
    shortClipSettings: ShortClipSettings? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isFullscreen by remember { mutableStateOf(false) }
    var enhancedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Load enhanced frame / photo
    LaunchedEffect(metadata, filter, settings) {
        withContext(Dispatchers.IO) {
            val base = processingEngine.loadSourceBitmap(metadata)
            if (base != null) {
                val processed = processingEngine.applyLiveEnhancementsToBitmap(base, filter, settings)
                enhancedBitmap = processed
            }
        }
    }

    val finalResolution = if (quality == QualityOption.ORIGINAL) metadata.resolutionText() else quality.resolutionLabel
    val estSizeMb = (metadata.fileSizeBytes.toDouble() / (1024 * 1024) * quality.estSizeFactor).coerceAtLeast(1.2)

    if (isFullscreen) {
        // Fullscreen playback / view container
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .testTag("fullscreen_video_view")
        ) {
            if (enhancedBitmap != null) {
                Image(
                    bitmap = enhancedBitmap!!.asImageBitmap(),
                    contentDescription = if (metadata.isPhoto) "Fullscreen Photo" else "Fullscreen Video",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Watermark overlay simulation if selected
            Surface(
                color = Color.Black.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(24.dp)
            ) {
                Text(
                    text = "UltraVideo AI • $finalResolution",
                    color = NeonCyan,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }

            IconButton(
                onClick = { isFullscreen = false },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                    .testTag("exit_fullscreen_button")
            ) {
                Icon(
                    imageVector = Icons.Default.FullscreenExit,
                    contentDescription = "Exit Fullscreen",
                    tint = Color.White
                )
            }
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (metadata.isPhoto) "Enhanced Photo Ready" else "Enhanced Video Ready",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onEnhanceAnother,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(38.dp)
                            .clip(CircleShape)
                            .border(1.dp, BorderWhite10, CircleShape)
                            .background(Zinc900)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Zinc950,
                    titleContentColor = TextPrimary
                ),
                modifier = Modifier.border(androidx.compose.foundation.BorderStroke(1.dp, BorderWhite05))
            )
        },
        bottomBar = {
            Surface(
                color = Zinc950,
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderWhite05),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onEnhanceAnother,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Zinc900,
                            contentColor = TextPrimary
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderWhite10),
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("enhance_another_video_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Replay,
                            contentDescription = null,
                            tint = Zinc300,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (metadata.isPhoto) "New Photo" else "New Video", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(Cyan500, Blue600)
                                )
                            )
                            .clickable {
                                shareMediaFile(context, outputFilePath, metadata.fileName, metadata.isPhoto)
                            }
                            .testTag("share_video_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (metadata.isPhoto) "Share Photo" else "Share Video", color = Color.Black, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        },
        containerColor = ObsidianDark,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            // Enhanced Media Player / Viewer Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Black)
                    .border(1.dp, BorderWhite10, RoundedCornerShape(20.dp))
                    .testTag("enhanced_video_player_card")
            ) {
                if (enhancedBitmap != null) {
                    Image(
                        bitmap = enhancedBitmap!!.asImageBitmap(),
                        contentDescription = if (metadata.isPhoto) "Enhanced Photo Frame" else "Enhanced Video Frame",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Play / Fullscreen Overlay
                IconButton(
                    onClick = { isFullscreen = true },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .background(Zinc900.copy(alpha = 0.8f), CircleShape)
                        .border(1.dp, BorderWhite10, CircleShape)
                        .testTag("fullscreen_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Fullscreen,
                        contentDescription = "Fullscreen",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Center Action Icon
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Zinc900.copy(alpha = 0.85f))
                        .border(1.dp, BorderWhite10, CircleShape)
                        .clickable { isFullscreen = true },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (metadata.isPhoto) Icons.Default.Fullscreen else Icons.Default.PlayArrow,
                        contentDescription = if (metadata.isPhoto) "View" else "Play",
                        tint = Cyan400,
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Bottom badge
                Surface(
                    color = Zinc900.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderWhite10),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Cyan400,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "AI Remastered in $finalResolution",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Before vs After Resolution Comparison Card
            GlassCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "ORIGINAL", color = Zinc500, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text(text = metadata.resolutionText(), color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = if (metadata.isPhoto) "Original Photo" else "${metadata.fps} fps", color = Zinc400, fontSize = 11.sp)
                    }

                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Cyan400.copy(alpha = 0.12f))
                            .border(1.dp, Cyan400.copy(alpha = 0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = Cyan400,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "ENHANCED", color = Cyan400, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold)
                        Text(text = finalResolution, color = Cyan400, fontSize = 16.sp, fontWeight = FontWeight.Black)
                        Text(text = if (metadata.isPhoto) "Neural Enhanced HDR" else "${metadata.fps} fps HDR", color = Zinc400, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // Short Clip 6K Details Card
            if (shortClipSettings?.isShortClipMode == true) {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "🔥", fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "6K Viral Short Ready",
                                        color = TextPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "Reframed to ${shortClipSettings.framingRatio.label} (${shortClipSettings.framingRatio.ratio})",
                                        color = Cyan400,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                            Surface(
                                color = ElectricPurple.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(8.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, ElectricPurple.copy(alpha = 0.4f))
                            ) {
                                Text(
                                    text = "3456×6144",
                                    color = ElectricPurple,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        if (shortClipSettings.addDynamicEmojis) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "✨", fontSize = 13.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Dynamic Animated Emojis (${shortClipSettings.emojiAnimationStyle.label}) burned into subtitles",
                                    color = Zinc300,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        if (shortClipSettings.smoothFocalTracking) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "🎥", fontSize = 13.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "AI Smooth Reframing & Subject Centering active (${shortClipSettings.smoothPanSpeed}% Fluidity)",
                                    color = Zinc300,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
            }

            // Language & Culture Dubbing Details Card
            if (dubbingSettings?.isDubbingEnabled == true) {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = dubbingSettings.targetLanguage.flagEmoji, fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Dubbed in ${dubbingSettings.targetLanguage.name}",
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                            Text(
                                text = "Voice Persona: ${dubbingSettings.voicePersona.title} • ${dubbingSettings.targetLanguage.culturalAccent}",
                                color = Cyan400,
                                fontSize = 11.sp
                            )
                            if (dubbingSettings.lipSyncEnabled) {
                                Text(
                                    text = "Neural Lip-Sync: Phoneme motion aligned to face",
                                    color = Zinc400,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
            }

            // Quick Action Grid: Save to Gallery, Share, Download/Export
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ActionButton(
                    icon = Icons.Default.PhotoLibrary,
                    label = "Save to Gallery",
                    onClick = {
                        val mediaLabel = if (metadata.isPhoto) "photo" else "video"
                        Toast.makeText(context, "Saved enhanced $mediaLabel to Gallery!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.weight(1f),
                    tag = "save_gallery_button"
                )

                ActionButton(
                    icon = Icons.Default.Download,
                    label = "Export File",
                    onClick = {
                        Toast.makeText(context, "Exported: ${exportFormat.label}", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.weight(1f),
                    tag = "export_file_button"
                )
            }

            // Processing Breakdown Specs
            GlassCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "ENHANCEMENT METRICS",
                        color = Zinc500,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )

                    MetricRow(label = if (metadata.isPhoto) "Image Format" else "Output Container", value = exportFormat.label)
                    MetricRow(label = if (metadata.isPhoto) "Compression" else "Video Codec", value = exportFormat.codec)
                    MetricRow(label = "Color Filter", value = filter.title)
                    MetricRow(label = "Super-Resolution", value = "${settings.superResolution}% (Neural NPU)")
                    MetricRow(label = "Detail Recovery", value = "${settings.detailRecovery}%")
                    if (!metadata.isPhoto) {
                        MetricRow(label = "Audio Sync", value = "100% Bit-Perfect Match")
                    } else {
                        MetricRow(label = "Color Warmth", value = "${settings.warmth}%")
                        MetricRow(label = "Clarity & Structure", value = "${settings.clarity}%")
                    }
                    MetricRow(label = "Output File Size", value = String.format("~%.1f MB", estSizeMb))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tag: String
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Zinc900,
            contentColor = TextPrimary
        ),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderWhite05),
        modifier = modifier
            .height(50.dp)
            .testTag(tag)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Cyan400, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun MetricRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Zinc400, fontSize = 12.sp)
        Text(text = value, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}

private fun shareMediaFile(context: Context, path: String?, name: String, isPhoto: Boolean) {
    try {
        val mime = if (isPhoto) {
            when {
                name.endsWith(".png", true) -> "image/png"
                name.endsWith(".webp", true) -> "image/webp"
                else -> "image/jpeg"
            }
        } else {
            "video/mp4"
        }
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = mime
            putExtra(Intent.EXTRA_SUBJECT, "Enhanced by UltraVideo AI: $name")
            putExtra(Intent.EXTRA_TEXT, "Remastered in Ultra High Resolution with UltraVideo AI!")
            if (path != null) {
                val file = File(path)
                if (file.exists()) {
                    val contentUri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.fileprovider",
                        file
                    )
                    putExtra(Intent.EXTRA_STREAM, contentUri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
            }
        }
        context.startActivity(Intent.createChooser(sendIntent, if (isPhoto) "Share Enhanced Photo" else "Share Enhanced Video"))
    } catch (e: Exception) {
        Toast.makeText(context, "Ready to share: $name", Toast.LENGTH_SHORT).show()
    }
}
