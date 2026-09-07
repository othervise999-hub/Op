package com.example.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.billing.BillingManager
import com.example.engine.VideoProcessingEngine
import com.example.model.EnhancementSettings
import com.example.model.ExportFormat
import com.example.model.FilterType
import com.example.model.LanguageDubbingSettings
import com.example.model.QualityOption
import com.example.model.ShortClipSettings
import com.example.model.VideoMetadata
import com.example.ui.components.BeforeAfterSlider
import com.example.ui.components.CinematicFilterStrip
import com.example.ui.components.GlassCard
import com.example.ui.components.LanguageDubbingSection
import com.example.ui.components.PremiumDialog
import com.example.ui.components.PrivacyDialog
import com.example.ui.components.ShortClipFinderSection
import com.example.ui.components.ShortClipStudioDialog
import com.example.ui.theme.AmberGold
import com.example.ui.theme.Blue600
import com.example.ui.theme.BorderWhite05
import com.example.ui.theme.BorderWhite10
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Cyan500
import com.example.ui.theme.Cyan600
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.EmeraldGlow
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.ObsidianElevated
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.RadiantPink
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.Zinc100
import com.example.ui.theme.Zinc300
import com.example.ui.theme.Zinc400
import com.example.ui.theme.Zinc500
import com.example.ui.theme.Zinc800
import com.example.ui.theme.Zinc900
import com.example.ui.theme.Zinc950
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProcessingConfigScreen(
    metadata: VideoMetadata,
    processingEngine: VideoProcessingEngine,
    billingManager: BillingManager,
    onBack: () -> Unit,
    onStartEnhancement: (QualityOption, FilterType, EnhancementSettings, ExportFormat, Boolean, LanguageDubbingSettings?, ShortClipSettings?) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isPremium by billingManager.isPremium.collectAsStateWithLifecycle()

    var selectedQuality by remember { mutableStateOf(QualityOption.UHD_4K) }
    var selectedFilter by remember { mutableStateOf(FilterType.CINEMATIC) }
    var enhancementSettings by remember { mutableStateOf(EnhancementSettings()) }
    var exportFormat by remember {
        mutableStateOf(if (metadata.isPhoto) ExportFormat.JPG_HIGH else ExportFormat.MP4_H264)
    }
    var includeWatermark by remember { mutableStateOf(!isPremium) }

    var languageDubbingSettings by remember { mutableStateOf(LanguageDubbingSettings()) }
    var shortClipSettings by remember {
        mutableStateOf(
            ShortClipSettings(
                customStartMs = (metadata.durationMs * 0.05).toLong().coerceAtLeast(1000L),
                customEndMs = ((metadata.durationMs * 0.05).toLong() + 15000L).coerceAtMost(if (metadata.durationMs > 10000) metadata.durationMs else 30000L)
            )
        )
    }
    var showShortClipStudio by remember { mutableStateOf(false) }

    var showPremiumDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }

    // Section tab: 0 = Resolution, 1 = Filters, 2 = Tuning, 3 = Dubbing, 4 = Shorts & 6K, 5 = Export
    var selectedTab by remember { mutableStateOf(0) }

    // Live Bitmaps for Before/After
    var baseBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var enhancedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Load base frame
    LaunchedEffect(metadata) {
        withContext(Dispatchers.IO) {
            baseBitmap = processingEngine.loadSourceBitmap(metadata)
        }
    }

    // Recalculate enhanced preview frame whenever filters or sliders change
    LaunchedEffect(baseBitmap, selectedFilter, enhancementSettings) {
        val src = baseBitmap ?: return@LaunchedEffect
        withContext(Dispatchers.Default) {
            val result = processingEngine.applyLiveEnhancementsToBitmap(
                source = src,
                filter = selectedFilter,
                settings = enhancementSettings
            )
            enhancedBitmap = result
        }
    }

    val estOutputMb = (metadata.fileSizeBytes.toDouble() / (1024 * 1024) * selectedQuality.estSizeFactor).coerceAtLeast(4.0)

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = metadata.fileName,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                maxLines = 1
                            )
                            Text(
                                text = if (metadata.isPhoto) "${metadata.width}×${metadata.height} • Photo • ${metadata.formattedFileSize()}" else "${metadata.width}×${metadata.height} • ${metadata.fps} fps • ${metadata.formattedDuration()}",
                                color = Cyan400,
                                fontSize = 11.sp
                            )
                        }
                    },
                    navigationIcon = {
                        Box(
                            modifier = Modifier
                                .padding(start = 12.dp, end = 4.dp)
                                .size(36.dp)
                                .clip(CircleShape)
                                .border(1.dp, BorderWhite10, CircleShape)
                                .background(Zinc900)
                                .clickable(onClick = onBack)
                                .testTag("config_back_button"),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = TextPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    },
                    actions = {
                        if (selectedQuality.isCloud) {
                            Box(
                                modifier = Modifier
                                    .padding(end = 6.dp)
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, BorderWhite10, CircleShape)
                                    .background(Zinc900)
                                    .clickable { showPrivacyDialog = true },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = "Privacy Shield",
                                    tint = Cyan400,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(36.dp)
                                .clip(CircleShape)
                                .border(1.dp, BorderWhite10, CircleShape)
                                .background(Zinc900)
                                .clickable { showPremiumDialog = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isPremium) Icons.Default.WorkspacePremium else Icons.Default.Lock,
                                contentDescription = "Premium",
                                tint = if (isPremium) EmeraldGlow else AmberGold,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = ObsidianDark,
                        titleContentColor = TextPrimary
                    )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(BorderWhite05)
                )
            }
        },
        bottomBar = {
            // Persistent Bottom Action Bar (Sophisticated Dark)
            Surface(
                color = Zinc950,
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderWhite05),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    // Estimated Stats Strip
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                tint = Cyan400,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Est: ${selectedQuality.estProcessingTime}",
                                color = TextPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.VideoFile,
                                contentDescription = null,
                                tint = ElectricPurple,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = String.format("Size: ~%.1f MB", estOutputMb),
                                color = Zinc400,
                                fontSize = 12.sp
                            )
                        }

                        Surface(
                            color = if (selectedQuality.isCloud) ElectricPurple.copy(alpha = 0.2f) else Cyan400.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (selectedQuality.isCloud) ElectricPurple.copy(alpha = 0.4f) else Cyan400.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = if (selectedQuality.isCloud) "CLOUD NEURAL" else "LOCAL NPU",
                                color = if (selectedQuality.isCloud) ElectricPurple else Cyan400,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Primary Action Button (Gradient Cyan-500 to Blue-600)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (shortClipSettings.isShortClipMode) Brush.horizontalGradient(listOf(RadiantPink, Cyan500))
                                else Brush.horizontalGradient(listOf(Cyan500, Blue600))
                            )
                            .clickable {
                                if (selectedQuality.isPro && !isPremium) {
                                    showPremiumDialog = true
                                } else {
                                    onStartEnhancement(
                                        selectedQuality,
                                        selectedFilter,
                                        enhancementSettings,
                                        exportFormat,
                                        includeWatermark,
                                        languageDubbingSettings,
                                        shortClipSettings
                                    )
                                }
                            }
                            .testTag("enhance_video_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            val buttonText = if (selectedQuality.isPro && !isPremium) {
                                "UNLOCK ${selectedQuality.title.uppercase()} (PRO)"
                            } else if (shortClipSettings.isShortClipMode) {
                                "EXPORT 6K VIRAL SHORT (3456×6144)"
                            } else if (languageDubbingSettings.isDubbingEnabled) {
                                "DUB IN ${languageDubbingSettings.targetLanguage.code} & ENHANCE"
                            } else if (metadata.isPhoto) {
                                "ENHANCE PHOTO TO ${selectedQuality.title.uppercase()}"
                            } else {
                                "ENHANCE TO ${selectedQuality.title.uppercase()}"
                            }
                            Text(
                                text = buttonText,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black,
                                letterSpacing = (-0.3).sp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .background(Color.Black, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "AI COMPUTING LOCAL + CLOUD (H.265 SECURE)",
                        color = Zinc500,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.5.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
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
        ) {
            // 1. Large Before / After Comparison Preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                BeforeAfterSlider(
                    originalBitmap = baseBitmap,
                    enhancedBitmap = enhancedBitmap,
                    enhancedQualityLabel = selectedQuality.resolutionLabel,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // 2. Video Metadata Stats 4-Column Grid (Sophisticated Dark)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, BorderWhite05, RoundedCornerShape(16.dp))
                    .background(Zinc900.copy(alpha = 0.5f))
                    .padding(vertical = 10.dp, horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "RES", color = Zinc500, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = metadata.resolutionText(), color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                }
                Box(modifier = Modifier.width(1.dp).height(24.dp).background(BorderWhite05))
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = if (metadata.isPhoto) "TYPE" else "FPS", color = Zinc500, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = if (metadata.isPhoto) "PHOTO" else "${metadata.fps}", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                }
                Box(modifier = Modifier.width(1.dp).height(24.dp).background(BorderWhite05))
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "SIZE", color = Zinc500, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = metadata.formattedFileSize(), color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                }
                Box(modifier = Modifier.width(1.dp).height(24.dp).background(BorderWhite05))
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = if (metadata.isPhoto) "ASPECT" else "TIME", color = Zinc500, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = if (metadata.isPhoto) metadata.aspectRatio else metadata.formattedDuration(), color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Quick Feature Badges (6K Short Clip Studio & AI Dubbing)
            if (!metadata.isPhoto) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        color = if (shortClipSettings.isShortClipMode) RadiantPink.copy(alpha = 0.2f) else Zinc900,
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (shortClipSettings.isShortClipMode) RadiantPink else BorderWhite10),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { showShortClipStudio = true }
                            .testTag("open_shorts_studio_quick_badge")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🔥", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = "6K Shorts Studio",
                                    color = if (shortClipSettings.isShortClipMode) RadiantPink else TextPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Auto-reframing & Emojis",
                                    color = Zinc500,
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }

                    Surface(
                        color = if (languageDubbingSettings.isDubbingEnabled) ElectricPurple.copy(alpha = 0.2f) else Zinc900,
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (languageDubbingSettings.isDubbingEnabled) ElectricPurple else BorderWhite10),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedTab = 3 }
                            .testTag("open_dubbing_quick_badge")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = languageDubbingSettings.targetLanguage.flagEmoji, fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = "AI Dubbing",
                                    color = if (languageDubbingSettings.isDubbingEnabled) ElectricPurple else TextPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${languageDubbingSettings.targetLanguage.name.take(13)}...",
                                    color = Zinc500,
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // 3. Navigation Tabs (Resolution, Filters, Tuning, Dubbing, Shorts & 6K, Export)
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Zinc900,
                contentColor = Cyan400,
                edgePadding = 16.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Cyan400
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, BorderWhite05, RoundedCornerShape(12.dp))
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Resolution & AI", fontWeight = FontWeight.Bold, fontSize = 12.sp) },
                    selectedContentColor = Cyan400,
                    unselectedContentColor = Zinc400
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Cinematic Filters", fontWeight = FontWeight.Bold, fontSize = 12.sp) },
                    selectedContentColor = Cyan400,
                    unselectedContentColor = Zinc400
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = { Text(if (metadata.isPhoto) "Photo & Light Tuning" else "AI Controls", fontWeight = FontWeight.Bold, fontSize = 12.sp) },
                    selectedContentColor = Cyan400,
                    unselectedContentColor = Zinc400
                )
                Tab(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("AI Dubbing", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            if (languageDubbingSettings.isDubbingEnabled) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(languageDubbingSettings.targetLanguage.flagEmoji, fontSize = 10.sp)
                            }
                        }
                    },
                    selectedContentColor = Cyan400,
                    unselectedContentColor = Zinc400
                )
                if (!metadata.isPhoto) {
                    Tab(
                        selected = selectedTab == 4,
                        onClick = { selectedTab = 4 },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Shorts & 6K", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                if (shortClipSettings.isShortClipMode) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("🔥", fontSize = 10.sp)
                                }
                            }
                        },
                        selectedContentColor = RadiantPink,
                        unselectedContentColor = Zinc400
                    )
                }
                Tab(
                    selected = selectedTab == (if (metadata.isPhoto) 4 else 5),
                    onClick = { selectedTab = if (metadata.isPhoto) 4 else 5 },
                    text = { Text("Export Options", fontWeight = FontWeight.Bold, fontSize = 12.sp) },
                    selectedContentColor = Cyan400,
                    unselectedContentColor = Zinc400
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTab) {
                0 -> QualitySection(
                    selectedQuality = selectedQuality,
                    onSelectQuality = { q ->
                        if (q.isPro && !isPremium) {
                            showPremiumDialog = true
                        }
                        selectedQuality = q
                    },
                    isPremium = isPremium
                )

                1 -> CinematicFilterStrip(
                    selectedFilter = selectedFilter,
                    onSelectFilter = { f ->
                        if (f.isPro && !isPremium) {
                            showPremiumDialog = true
                        }
                        selectedFilter = f
                    },
                    isPremium = isPremium,
                    showHeader = true
                )

                2 -> AiEnhancementSlidersSection(
                    settings = enhancementSettings,
                    onSettingsChange = { enhancementSettings = it },
                    isPhoto = metadata.isPhoto
                )

                3 -> LanguageDubbingSection(
                    settings = languageDubbingSettings,
                    onSettingsChange = { languageDubbingSettings = it }
                )

                4 -> if (!metadata.isPhoto) {
                    ShortClipFinderSection(
                        metadata = metadata,
                        settings = shortClipSettings,
                        onSettingsChange = { shortClipSettings = it },
                        onOpenStudio = { showShortClipStudio = true }
                    )
                } else {
                    ExportOptionsSection(
                        exportFormat = exportFormat,
                        onFormatChange = { exportFormat = it },
                        includeWatermark = includeWatermark,
                        onWatermarkChange = { includeWatermark = it },
                        isPremium = isPremium,
                        onUpgradeClick = { showPremiumDialog = true },
                        selectedQuality = selectedQuality,
                        isPhoto = metadata.isPhoto
                    )
                }

                5 -> ExportOptionsSection(
                    exportFormat = exportFormat,
                    onFormatChange = { exportFormat = it },
                    includeWatermark = includeWatermark,
                    onWatermarkChange = { includeWatermark = it },
                    isPremium = isPremium,
                    onUpgradeClick = { showPremiumDialog = true },
                    selectedQuality = selectedQuality,
                    isPhoto = metadata.isPhoto
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showPremiumDialog) {
        PremiumDialog(billingManager = billingManager, onDismiss = { showPremiumDialog = false })
    }

    if (showPrivacyDialog) {
        PrivacyDialog(onDismiss = { showPrivacyDialog = false })
    }

    if (showShortClipStudio) {
        ShortClipStudioDialog(
            metadata = metadata,
            previewBitmap = enhancedBitmap ?: baseBitmap,
            settings = shortClipSettings,
            onSettingsChange = { shortClipSettings = it },
            onDismiss = { showShortClipStudio = false },
            onApplyAndExport = {
                showShortClipStudio = false
                shortClipSettings = shortClipSettings.copy(isShortClipMode = true)
                onStartEnhancement(
                    QualityOption.UHD_6K,
                    selectedFilter,
                    enhancementSettings,
                    exportFormat,
                    includeWatermark,
                    languageDubbingSettings,
                    shortClipSettings.copy(isShortClipMode = true)
                )
            }
        )
    }
}

@Composable
private fun MetaBadge(label: String, value: String) {
    Surface(
        color = ObsidianElevated,
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(text = "$label: ", color = TextMuted, fontSize = 10.sp)
            Text(text = value, color = TextPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun QualitySection(
    selectedQuality: QualityOption,
    onSelectQuality: (QualityOption) -> Unit,
    isPremium: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // QUALITY SELECTION
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TARGET RESOLUTION",
                    color = TextMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp
                )
                Text(
                    text = "AI Multi-Frame Upscaling",
                    color = NeonCyan,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(QualityOption.values()) { option ->
                    val isSelected = option == selectedQuality
                    QualityCard(
                        option = option,
                        isSelected = isSelected,
                        isPremium = isPremium,
                        onClick = { onSelectQuality(option) }
                    )
                }
            }

            if (selectedQuality.isCloud) {
                Surface(
                    color = ElectricPurple.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ElectricPurple.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CloudQueue,
                            contentDescription = null,
                            tint = ElectricPurple,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "${selectedQuality.title} uses sandboxed neural cloud super-resolution clusters. Fully encrypted & auto-deleted after render.",
                            color = TextPrimary,
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QualityCard(
    option: QualityOption,
    isSelected: Boolean,
    isPremium: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) Cyan400.copy(alpha = 0.8f) else BorderWhite05
    val bgColor = if (isSelected) Cyan500.copy(alpha = 0.12f) else Zinc900

    Box(
        modifier = Modifier
            .width(115.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(if (isSelected) 1.5.dp else 1.dp, borderColor, RoundedCornerShape(16.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(12.dp)
            .testTag("quality_card_${option.name}")
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = if (option.isCloud) ElectricPurple.copy(alpha = 0.25f) else Cyan400.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = option.badge,
                        color = if (option.isCloud) ElectricPurple else Cyan400,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                    )
                }

                if (option.isPro && !isPremium) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Pro only",
                        tint = AmberGold,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = option.title,
                color = if (isSelected) Cyan400 else TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
            Text(
                text = option.resolutionLabel,
                color = if (isSelected) Cyan400.copy(alpha = 0.8f) else Zinc500,
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = option.estProcessingTime,
                color = if (isSelected) Cyan400 else Zinc400,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun FilterCard(
    filter: FilterType,
    isSelected: Boolean,
    isPremium: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) Cyan400 else BorderWhite05
    val swatchColor = Color(filter.previewHexColor)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(76.dp)
            .clickable(onClick = onClick)
            .testTag("filter_card_${filter.name}")
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(if (isSelected) 2.dp else 1.dp, borderColor, RoundedCornerShape(16.dp))
                .background(
                    Brush.radialGradient(
                        colors = listOf(swatchColor, Zinc950)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            if (filter.isPro && !isPremium) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color.Black.copy(alpha = 0.6f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Pro",
                        tint = AmberGold,
                        modifier = Modifier.size(11.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = filter.title,
            color = if (isSelected) Cyan400 else Zinc400,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            maxLines = 1
        )
    }
}

@Composable
private fun AiEnhancementSlidersSection(
    settings: EnhancementSettings,
    onSettingsChange: (EnhancementSettings) -> Unit,
    isPhoto: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Presets bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PresetButton("Cinema Master") {
                onSettingsChange(
                    settings.copy(
                        superResolution = 90,
                        sharpness = 75,
                        detailRecovery = 85,
                        contrast = 60,
                        hdrEnhancement = 75,
                        noiseReduction = 65
                    )
                )
            }
            PresetButton("Face & Portrait") {
                onSettingsChange(
                    settings.copy(
                        faceEnhancement = 95,
                        noiseReduction = 80,
                        sharpness = 60,
                        detailRecovery = 70,
                        warmth = 55,
                        exposure = 54
                    )
                )
            }
            PresetButton("Vintage Film") {
                onSettingsChange(
                    settings.copy(
                        warmth = 65,
                        contrast = 58,
                        clarity = 60,
                        vignette = 30,
                        filmGrain = 25,
                        exposure = 52
                    )
                )
            }
            PresetButton("HDR Landscape") {
                onSettingsChange(
                    settings.copy(
                        clarity = 75,
                        highlights = 40,
                        shadows = 68,
                        saturation = 62,
                        hdrEnhancement = 80
                    )
                )
            }
            PresetButton("Action Crisp") {
                onSettingsChange(
                    settings.copy(
                        motionEnhancement = 90,
                        sharpness = 85,
                        superResolution = 90,
                        detailRecovery = 85
                    )
                )
            }
            PresetButton("Reset") {
                onSettingsChange(EnhancementSettings())
            }
        }

        // Section: Photo & Light Tuning
        Text(
            text = "LIGHT & COLOR BALANCE",
            color = Zinc500,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp
        )

        EnhancementSliderItem(
            title = "Exposure",
            value = settings.exposure,
            onValueChange = { onSettingsChange(settings.copy(exposure = it)) },
            tag = "slider_exposure"
        )
        EnhancementSliderItem(
            title = "Contrast",
            value = settings.contrast,
            onValueChange = { onSettingsChange(settings.copy(contrast = it)) },
            tag = "slider_contrast"
        )
        EnhancementSliderItem(
            title = "Highlights",
            value = settings.highlights,
            onValueChange = { onSettingsChange(settings.copy(highlights = it)) },
            tag = "slider_highlights"
        )
        EnhancementSliderItem(
            title = "Shadows Recovery",
            value = settings.shadows,
            onValueChange = { onSettingsChange(settings.copy(shadows = it)) },
            tag = "slider_shadows"
        )
        EnhancementSliderItem(
            title = "Warmth (Color Temp)",
            value = settings.warmth,
            onValueChange = { onSettingsChange(settings.copy(warmth = it)) },
            tag = "slider_warmth"
        )
        EnhancementSliderItem(
            title = "Tint (Green - Magenta)",
            value = settings.tint,
            onValueChange = { onSettingsChange(settings.copy(tint = it)) },
            tag = "slider_tint"
        )
        EnhancementSliderItem(
            title = "Saturation",
            value = settings.saturation,
            onValueChange = { onSettingsChange(settings.copy(saturation = it)) },
            tag = "slider_saturation"
        )
        EnhancementSliderItem(
            title = "Clarity & Structure",
            value = settings.clarity,
            onValueChange = { onSettingsChange(settings.copy(clarity = it)) },
            tag = "slider_clarity"
        )
        EnhancementSliderItem(
            title = "Lens Vignette",
            value = settings.vignette,
            onValueChange = { onSettingsChange(settings.copy(vignette = it)) },
            tag = "slider_vignette"
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Section: AI Neural Enhancements
        Text(
            text = "AI NEURAL ENHANCEMENTS",
            color = Zinc500,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp
        )

        EnhancementSliderItem(
            title = "AI Super Resolution",
            value = settings.superResolution,
            onValueChange = { onSettingsChange(settings.copy(superResolution = it)) },
            tag = "slider_super_res"
        )
        EnhancementSliderItem(
            title = "Sharpness",
            value = settings.sharpness,
            onValueChange = { onSettingsChange(settings.copy(sharpness = it)) },
            tag = "slider_sharpness"
        )
        EnhancementSliderItem(
            title = "Detail Recovery",
            value = settings.detailRecovery,
            onValueChange = { onSettingsChange(settings.copy(detailRecovery = it)) },
            tag = "slider_detail_recovery"
        )
        EnhancementSliderItem(
            title = "Noise Reduction",
            value = settings.noiseReduction,
            onValueChange = { onSettingsChange(settings.copy(noiseReduction = it)) },
            tag = "slider_noise_reduction"
        )
        EnhancementSliderItem(
            title = "Face & Portrait Restoration",
            value = settings.faceEnhancement,
            onValueChange = { onSettingsChange(settings.copy(faceEnhancement = it)) },
            tag = "slider_face_enhance"
        )
        if (!isPhoto) {
            EnhancementSliderItem(
                title = "Motion Enhancement",
                value = settings.motionEnhancement,
                onValueChange = { onSettingsChange(settings.copy(motionEnhancement = it)) },
                tag = "slider_motion_enhance"
            )
        }
        EnhancementSliderItem(
            title = "HDR Dynamic Range",
            value = settings.hdrEnhancement,
            onValueChange = { onSettingsChange(settings.copy(hdrEnhancement = it)) },
            tag = "slider_hdr_enhance"
        )
        EnhancementSliderItem(
            title = "Brightness",
            value = settings.brightness,
            onValueChange = { onSettingsChange(settings.copy(brightness = it)) },
            tag = "slider_brightness"
        )
    }
}

@Composable
private fun PresetButton(name: String, onClick: () -> Unit) {
    Surface(
        color = Zinc900,
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderWhite10),
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = name,
            color = Zinc300,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
        )
    }
}

@Composable
private fun EnhancementSliderItem(
    title: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    tag: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(tag)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, color = Zinc300, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Text(text = "$value%", color = Cyan400, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(
                thumbColor = Cyan400,
                activeTrackColor = Cyan400,
                inactiveTrackColor = Zinc800
            )
        )
    }
}

@Composable
private fun ExportOptionsSection(
    exportFormat: ExportFormat,
    onFormatChange: (ExportFormat) -> Unit,
    includeWatermark: Boolean,
    onWatermarkChange: (Boolean) -> Unit,
    isPremium: Boolean,
    onUpgradeClick: () -> Unit,
    selectedQuality: QualityOption,
    isPhoto: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = if (isPhoto) "IMAGE FORMAT & ENCODING" else "CONTAINER & CODEC",
            color = Zinc500,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp
        )

        val availableFormats = ExportFormat.values().filter { it.isPhotoFormat == isPhoto }

        availableFormats.forEach { fmt ->
            val isSelected = fmt == exportFormat
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onFormatChange(fmt) },
                borderColor = if (isSelected) Cyan400 else BorderWhite05
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = fmt.label, color = if (isSelected) Cyan400 else TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = fmt.codec, color = Zinc500, fontSize = 11.sp)
                    }
                    if (isSelected) {
                        Surface(color = Cyan400.copy(alpha = 0.15f), shape = CircleShape) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = Cyan400,
                                modifier = Modifier
                                    .padding(6.dp)
                                    .size(16.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "WATERMARK & BRANDING",
            color = Zinc500,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp
        )

        GlassCard(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (!isPremium) {
                    onUpgradeClick()
                } else {
                    onWatermarkChange(!includeWatermark)
                }
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (includeWatermark) "UltraVideo AI Watermark Enabled" else "Clean Export (No Watermark)",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Text(
                        text = if (!isPremium) "Pro removes watermark completely" else "Tap to toggle watermark off",
                        color = Zinc500,
                        fontSize = 11.sp
                    )
                }
                if (!isPremium) {
                    Surface(color = AmberGold.copy(alpha = 0.15f), shape = RoundedCornerShape(6.dp), border = androidx.compose.foundation.BorderStroke(1.dp, AmberGold.copy(alpha = 0.4f))) {
                        Text(
                            text = "PRO ONLY",
                            color = AmberGold,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
