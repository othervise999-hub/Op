package com.example.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
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
import com.example.data.ProjectEntity
import com.example.data.ProjectRepository
import com.example.engine.VideoProcessingEngine
import com.example.ui.components.GlassCard
import com.example.ui.components.PremiumDialog
import com.example.ui.components.PrivacyDialog
import com.example.ui.theme.AmberGold
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class SampleMedia(
    val title: String,
    val resolution: String,
    val durationOrType: String,
    val drawableRes: Int,
    val isPhoto: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    repository: ProjectRepository,
    billingManager: BillingManager,
    processingEngine: VideoProcessingEngine,
    onSelectVideoUri: (Uri, Boolean, Int?) -> Unit,
    onOpenProject: (ProjectEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isPremium by billingManager.isPremium.collectAsStateWithLifecycle()
    val projects by repository.allProjects.collectAsStateWithLifecycle(initialValue = emptyList())

    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showPremiumDialog by remember { mutableStateOf(false) }
    var selectedMediaFilter by remember { mutableStateOf("All") }

    // Modern Photo & Video Picker (Any visual media)
    val mediaPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            onSelectVideoUri(uri, false, null)
        }
    }

    // Video Only Picker
    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            onSelectVideoUri(uri, false, null)
        }
    }

    // Photo Only Picker
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            onSelectVideoUri(uri, false, null)
        }
    }

    // OpenDocument / File Picker fallback
    val docPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            onSelectVideoUri(uri, false, null)
        }
    }

    val sampleMediaList = remember {
        listOf(
            SampleMedia("Cyberpunk Metropolis", "1080p FHD", "00:28", R.drawable.sample_cyberpunk, isPhoto = false),
            SampleMedia("Alpine Morning Mist", "4K UHD", "00:42", R.drawable.sample_nature, isPhoto = false),
            SampleMedia("Studio Portrait Pro", "12 MP", "Photo", R.drawable.sample_portrait, isPhoto = true),
            SampleMedia("Golden Coast Vista", "24 MP", "Photo", R.drawable.sample_scenic, isPhoto = true)
        )
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .background(
                                        brush = Brush.linearGradient(listOf(Cyan400, Blue600)),
                                        shape = RoundedCornerShape(9.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(15.dp)
                                        .rotate(45f)
                                        .border(2.dp, Color.White, RoundedCornerShape(2.dp))
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "UltraVideo",
                                        color = TextPrimary,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = (-0.5).sp,
                                        fontSize = 18.sp
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "AI",
                                        color = Cyan400,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 18.sp
                                    )
                                }
                                Text(
                                    text = "Super-Resolution & Remaster",
                                    color = Zinc500,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    },
                    actions = {
                        // Privacy Policy Round Zinc Button
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .border(1.dp, BorderWhite10, CircleShape)
                                .background(Zinc900)
                                .clickable { showPrivacyDialog = true }
                                .testTag("home_privacy_button"),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = "Privacy Shield",
                                tint = Zinc400,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Temporary file cleanup Round Zinc Button
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .border(1.dp, BorderWhite10, CircleShape)
                                .background(Zinc900)
                                .clickable {
                                    val bytes = processingEngine.cleanTemporaryFiles()
                                    val mb = bytes.toDouble() / (1024 * 1024)
                                    Toast.makeText(
                                        context,
                                        if (bytes > 0) String.format("Cleaned %.1f MB of temporary cache", mb) else "Cache is clean",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .testTag("home_clean_cache_button"),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CleaningServices,
                                contentDescription = "Clean Cache",
                                tint = Zinc400,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Pro Upgrade Button
                        Surface(
                            color = if (isPremium) EmeraldGlow.copy(alpha = 0.15f) else AmberGold.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(20.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isPremium) EmeraldGlow.copy(alpha = 0.4f) else AmberGold.copy(alpha = 0.4f)),
                            modifier = Modifier
                                .clickable { showPremiumDialog = true }
                                .padding(end = 8.dp)
                                .testTag("home_pro_badge_button")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = if (isPremium) Icons.Default.Star else Icons.Default.WorkspacePremium,
                                    contentDescription = null,
                                    tint = if (isPremium) EmeraldGlow else AmberGold,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isPremium) "VIP PRO" else "GO PRO",
                                    color = if (isPremium) EmeraldGlow else AmberGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = ObsidianDark,
                        titleContentColor = TextPrimary
                    )
                )
                // Subtle bottom border matching Sophisticated Dark header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(BorderWhite05)
                )
            }
        },
        containerColor = ObsidianDark,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Hero Banner
            item {
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .testTag("hero_banner_card")
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hero_ai_video),
                        contentDescription = "UltraVideo AI Showcase",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        ObsidianDark.copy(alpha = 0.9f),
                                        ObsidianDark.copy(alpha = 0.4f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            color = NeonCyan.copy(alpha = 0.25f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "NEURAL SUPER-RESOLUTION",
                                color = NeonCyan,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Upscale Video & Photos",
                            color = TextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "AI 4K/8K Super-Res, micro-texture recovery & color grading",
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // Upload & Drop Area
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "IMPORT MEDIA",
                        color = TextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )

                    // Drag/Drop Style Upload Area
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .border(
                                1.dp,
                                BorderWhite10,
                                RoundedCornerShape(24.dp)
                            )
                            .background(Zinc900.copy(alpha = 0.7f))
                            .clickable {
                                mediaPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                                )
                            }
                            .testTag("drag_drop_upload_area"),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(Cyan400.copy(alpha = 0.12f), CircleShape)
                                    .border(1.dp, Cyan400.copy(alpha = 0.3f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CloudUpload,
                                    contentDescription = "Upload",
                                    tint = Cyan400,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Select or Drop Media Here",
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Video (MP4, MOV) & Photo (JPG, PNG, WEBP) • Up to 8K Zero Loss",
                                color = Zinc500,
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Direct Action Buttons: Video vs Photo
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Video Upload Button
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Brush.horizontalGradient(listOf(Cyan500, Blue600)))
                                .clickable {
                                    videoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                                    )
                                }
                                .testTag("upload_video_button"),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VideoLibrary,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(17.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    "Enhance Video",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp,
                                    color = Color.Black,
                                    letterSpacing = (-0.2).sp
                                )
                            }
                        }

                        // Photo Upload Button
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Brush.horizontalGradient(listOf(ElectricPurple, Cyan500)))
                                .clickable {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }
                                .testTag("upload_photo_button"),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddPhotoAlternate,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(17.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    "Enhance Photo",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp,
                                    color = Color.White,
                                    letterSpacing = (-0.2).sp
                                )
                            }
                        }
                    }

                    // Open Files / Document Picker Fallback
                    OutlinedButton(
                        onClick = {
                            docPickerLauncher.launch(arrayOf("video/*", "image/*"))
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Zinc900,
                            contentColor = TextPrimary
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderWhite10),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("choose_gallery_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.CloudUpload,
                            contentDescription = null,
                            tint = Cyan400,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Browse All Files & Folders",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = TextPrimary
                        )
                    }
                }
            }

            // Quick Demo Samples
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "TRY DEMO MEDIA",
                            color = TextMuted,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp
                        )

                        // Filter Chips Row
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("All", "Photos", "Videos").forEach { filter ->
                                val isSelected = selectedMediaFilter == filter
                                Surface(
                                    color = if (isSelected) Cyan400.copy(alpha = 0.2f) else Zinc900,
                                    shape = RoundedCornerShape(12.dp),
                                    border = androidx.compose.foundation.BorderStroke(
                                        1.dp,
                                        if (isSelected) Cyan400 else BorderWhite05
                                    ),
                                    modifier = Modifier.clickable { selectedMediaFilter = filter }
                                ) {
                                    Text(
                                        text = filter,
                                        color = if (isSelected) Cyan400 else Zinc400,
                                        fontSize = 10.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }

                    val filteredMedia = sampleMediaList.filter { sample ->
                        when (selectedMediaFilter) {
                            "Photos" -> sample.isPhoto
                            "Videos" -> !sample.isPhoto
                            else -> true
                        }
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filteredMedia) { sample ->
                            SampleCard(
                                sample = sample,
                                onClick = {
                                    val demoUri = Uri.parse("android.resource://${context.packageName}/${sample.drawableRes}")
                                    onSelectVideoUri(demoUri, true, sample.drawableRes)
                                }
                            )
                        }
                    }
                }
            }

            // New AI Capabilities: Global Culture Dubbing & 6K Short Clip Finder
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "NEW AI CAPABILITIES",
                        color = TextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // 6K Shorts Auto-Edit Card
                        GlassCard(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    // Trigger video picker to create shorts
                                    videoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                                    )
                                }
                                .testTag("home_feature_shorts_card")
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "🔥", fontSize = 20.sp)
                                    Surface(
                                        color = RadiantPink.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(6.dp),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, RadiantPink.copy(alpha = 0.4f))
                                    ) {
                                        Text(
                                            text = "6K UHD",
                                            color = RadiantPink,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Shorts Finder",
                                    color = TextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Auto-clip viral parts, dynamic emojis & 9:16 smooth pan",
                                    color = Zinc400,
                                    fontSize = 10.sp,
                                    lineHeight = 14.sp
                                )
                            }
                        }

                        // AI Global Dubbing Card
                        GlassCard(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    // Trigger video picker to dub
                                    videoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                                    )
                                }
                                .testTag("home_feature_dubbing_card")
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "🌍", fontSize = 20.sp)
                                    Surface(
                                        color = ElectricPurple.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(6.dp),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, ElectricPurple.copy(alpha = 0.4f))
                                    ) {
                                        Text(
                                            text = "29+ CULTURES",
                                            color = ElectricPurple,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "AI Dubbing",
                                    color = TextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Translate & dub voice across global cultures with lip-sync",
                                    color = Zinc400,
                                    fontSize = 10.sp,
                                    lineHeight = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            // Recent Projects
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "RECENT PROJECTS (${projects.size})",
                        color = TextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )

                    if (projects.isEmpty()) {
                        GlassCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VideoLibrary,
                                    contentDescription = null,
                                    tint = TextMuted,
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "No Remastered Projects Yet",
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Enhance your first clip or try a demo above to build your 4K/9K portfolio.",
                                    color = TextSecondary,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    } else {
                        projects.forEach { project ->
                            ProjectCard(
                                project = project,
                                onClick = { onOpenProject(project) },
                                onDelete = {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        repository.delete(project.id)
                                    }
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }

    if (showPrivacyDialog) {
        PrivacyDialog(onDismiss = { showPrivacyDialog = false })
    }

    if (showPremiumDialog) {
        PremiumDialog(billingManager = billingManager, onDismiss = { showPremiumDialog = false })
    }
}

@Composable
private fun SampleCard(
    sample: SampleMedia,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .height(130.dp)
            .border(1.dp, BorderWhite05, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("sample_card_${sample.title.replace(" ", "_")}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Zinc900)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = sample.drawableRes),
                contentDescription = sample.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, ObsidianDark.copy(alpha = 0.85f))
                        )
                    )
            )

            // Play / Photo badge
            Surface(
                color = Color.Black.copy(alpha = 0.55f),
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(36.dp)
            ) {
                Icon(
                    imageVector = if (sample.isPhoto) Icons.Default.AutoAwesome else Icons.Default.PlayArrow,
                    contentDescription = if (sample.isPhoto) "Photo" else "Play",
                    tint = if (sample.isPhoto) Cyan400 else NeonCyan,
                    modifier = Modifier.padding(7.dp)
                )
            }

            // Bottom metadata
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                Text(
                    text = sample.title,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = sample.resolution,
                        color = NeonCyan,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "• ${sample.durationOrType}",
                        color = TextMuted,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun ProjectCard(
    project: ProjectEntity,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("project_card_${project.id}"),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ObsidianDark),
                contentAlignment = Alignment.Center
            ) {
                if (project.thumbnailResId != null) {
                    Image(
                        painter = painterResource(id = project.thumbnailResId),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.VideoLibrary,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = project.title,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${project.originalResolution} →",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Surface(
                        color = NeonCyan.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = project.targetQuality,
                            color = NeonCyan,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Filter: ${project.filterType} • Sharp: ${project.sharpness}%",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }

            // Delete
            IconButton(
                onClick = onDelete,
                modifier = Modifier.testTag("delete_project_${project.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = TextMuted,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
