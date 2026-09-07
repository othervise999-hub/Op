package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.CropLandscape
import androidx.compose.material.icons.filled.CropPortrait
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Hd
import androidx.compose.material.icons.filled.MovieFilter
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VideoCameraFront
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.EmojiAnimationStyle
import com.example.model.FramingRatio
import com.example.model.QualityOption
import com.example.model.ShortClipAnalyzer
import com.example.model.ShortClipSettings
import com.example.model.VideoMetadata
import com.example.model.ViralClipSegment
import com.example.ui.theme.AmberGold
import com.example.ui.theme.Blue600
import com.example.ui.theme.BorderWhite05
import com.example.ui.theme.BorderWhite10
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Cyan500
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.EmeraldGlow
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.RadiantPink
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.Zinc300
import com.example.ui.theme.Zinc400
import com.example.ui.theme.Zinc500
import com.example.ui.theme.Zinc800
import com.example.ui.theme.Zinc900
import com.example.ui.theme.Zinc950

@Composable
fun ShortClipFinderSection(
    metadata: VideoMetadata,
    settings: ShortClipSettings,
    onSettingsChange: (ShortClipSettings) -> Unit,
    onOpenStudio: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val viralSegments = remember(metadata) {
        ShortClipAnalyzer.generateViralSegmentsForMedia(metadata)
    }

    val maxDurationMs = (metadata.durationMs.takeIf { it > 5000 } ?: 30000L).toFloat()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Master Enable Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Zinc900),
            border = BorderStroke(
                1.dp,
                if (settings.isShortClipMode) Brush.horizontalGradient(listOf(RadiantPink, Cyan400))
                else BorderStroke(1.dp, BorderWhite05).brush
            ),
            modifier = Modifier.fillMaxWidth().testTag("short_clip_master_card")
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(
                                if (settings.isShortClipMode)
                                    Brush.linearGradient(listOf(RadiantPink, Cyan500))
                                else
                                    Brush.linearGradient(listOf(Zinc800, Zinc900))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCut,
                            contentDescription = "Shorts Finder",
                            tint = if (settings.isShortClipMode) Color.White else Zinc400,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Viral Short Clip Finder",
                                color = TextPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = RadiantPink.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(6.dp),
                                border = BorderStroke(1.dp, RadiantPink.copy(alpha = 0.4f))
                            ) {
                                Text(
                                    text = "6K AUTO-EDIT",
                                    color = RadiantPink,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Auto-detect viral moments, smooth reframing & animated emojis",
                            color = Zinc400,
                            fontSize = 11.sp
                        )
                    }
                }

                Switch(
                    checked = settings.isShortClipMode,
                    onCheckedChange = { onSettingsChange(settings.copy(isShortClipMode = it)) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = RadiantPink,
                        uncheckedThumbColor = Zinc400,
                        uncheckedTrackColor = Zinc800
                    ),
                    modifier = Modifier.testTag("short_clip_switch")
                )
            }
        }

        AnimatedVisibility(visible = settings.isShortClipMode) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                // 1. Detected Viral Segments List
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Whatshot,
                                contentDescription = null,
                                tint = AmberGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "DETECTED UNIQUE VIRAL MOMENTS",
                                color = TextMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }
                        Text(
                            text = "${viralSegments.size} Segments Found",
                            color = NeonCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        viralSegments.forEach { segment ->
                            val isSelected = settings.selectedSegmentId == segment.id
                            Surface(
                                color = if (isSelected) Cyan400.copy(alpha = 0.12f) else Zinc900,
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) Cyan400 else BorderWhite05
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onSettingsChange(
                                            settings.copy(
                                                selectedSegmentId = segment.id,
                                                customStartMs = segment.startMs,
                                                customEndMs = segment.endMs
                                            )
                                        )
                                    }
                                    .testTag("viral_segment_${segment.id}")
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Viral Score Badge
                                    Box(
                                        modifier = Modifier
                                            .size(46.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(
                                                if (segment.viralScore >= 95)
                                                    Brush.verticalGradient(listOf(AmberGold, RadiantPink))
                                                else
                                                    Brush.verticalGradient(listOf(Cyan500, Blue600))
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = "${segment.viralScore}%",
                                                color = Color.White,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Black
                                            )
                                            Text(
                                                text = segment.emojiTag,
                                                fontSize = 13.sp
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = segment.title,
                                                color = if (isSelected) Cyan400 else TextPrimary,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp
                                            )
                                            Surface(
                                                color = Zinc950,
                                                shape = RoundedCornerShape(8.dp),
                                                border = BorderStroke(1.dp, BorderWhite05)
                                            ) {
                                                Text(
                                                    text = "${segment.durationFormatted()} • ${segment.timecodeRange()}",
                                                    color = Zinc300,
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(3.dp))
                                        Text(
                                            text = segment.hookSummary,
                                            color = Zinc400,
                                            fontSize = 11.sp,
                                            lineHeight = 14.sp
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Auto-Caption: \"${segment.hookCaption}\"",
                                            color = AmberGold,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // 2. Smooth Unique Part Scrubber & Framing (9:16)
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Zinc900),
                    border = BorderStroke(1.dp, BorderWhite05),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "SMOOTH UNIQUE PART TRIM & REFRAME",
                                color = TextMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            val durationSec = ((settings.customEndMs - settings.customStartMs) / 1000).coerceAtLeast(1)
                            Surface(
                                color = Cyan400.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, Cyan400.copy(alpha = 0.3f))
                            ) {
                                Text(
                                    text = "Clip Duration: ${durationSec}s",
                                    color = Cyan400,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        // Framing Aspect Ratio Chips (9:16 Vertical, 1:1, 16:9)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FramingRatio.values().forEach { ratio ->
                                val isSel = settings.framingRatio == ratio
                                Surface(
                                    color = if (isSel) Cyan400.copy(alpha = 0.2f) else Zinc950,
                                    shape = RoundedCornerShape(12.dp),
                                    border = BorderStroke(1.dp, if (isSel) Cyan400 else BorderWhite05),
                                    modifier = Modifier.weight(1f).clickable {
                                        onSettingsChange(settings.copy(framingRatio = ratio))
                                    }
                                ) {
                                    Column(
                                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            imageVector = when (ratio) {
                                                FramingRatio.VERTICAL_9_16 -> Icons.Default.CropPortrait
                                                FramingRatio.SQUARE_1_1 -> Icons.Default.CropSquare
                                                FramingRatio.CINEMATIC_16_9 -> Icons.Default.CropLandscape
                                            },
                                            contentDescription = null,
                                            tint = if (isSel) Cyan400 else Zinc400,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = ratio.label,
                                            color = if (isSel) Cyan400 else TextPrimary,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = ratio.ratio,
                                            color = Zinc500,
                                            fontSize = 9.sp
                                        )
                                    }
                                }
                            }
                        }

                        // Interactive Range Sliders (Start & End)
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Clip Start Marker", color = TextSecondary, fontSize = 12.sp)
                                val sSec = (settings.customStartMs / 1000).toInt()
                                Text("${sSec}s", color = Cyan400, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Slider(
                                value = settings.customStartMs.toFloat(),
                                onValueChange = {
                                    val newStart = it.toLong()
                                    if (newStart < settings.customEndMs - 2000L) {
                                        onSettingsChange(settings.copy(customStartMs = newStart))
                                    }
                                },
                                valueRange = 0f..maxDurationMs,
                                colors = SliderDefaults.colors(
                                    thumbColor = Cyan400,
                                    activeTrackColor = Cyan400,
                                    inactiveTrackColor = Zinc800
                                )
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Clip End Marker", color = TextSecondary, fontSize = 12.sp)
                                val eSec = (settings.customEndMs / 1000).toInt()
                                Text("${eSec}s", color = Cyan400, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Slider(
                                value = settings.customEndMs.toFloat(),
                                onValueChange = {
                                    val newEnd = it.toLong()
                                    if (newEnd > settings.customStartMs + 2000L) {
                                        onSettingsChange(settings.copy(customEndMs = newEnd))
                                    }
                                },
                                valueRange = 0f..maxDurationMs,
                                colors = SliderDefaults.colors(
                                    thumbColor = RadiantPink,
                                    activeTrackColor = RadiantPink,
                                    inactiveTrackColor = Zinc800
                                )
                            )
                        }

                        // Smooth Focal Tracking Switch ("edit smoothly unique part")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "AI Smooth Camera Easing & Subject Tracking",
                                    color = TextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Glides virtual 9:16 lens with cinematic ease-in/out to prevent jittery cuts",
                                    color = Zinc500,
                                    fontSize = 11.sp
                                )
                            }
                            Switch(
                                checked = settings.smoothFocalTracking,
                                onCheckedChange = { onSettingsChange(settings.copy(smoothFocalTracking = it)) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Cyan500,
                                    uncheckedThumbColor = Zinc400,
                                    uncheckedTrackColor = Zinc800
                                )
                            )
                        }

                        if (settings.smoothFocalTracking) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Camera Pan Fluidity", color = TextSecondary, fontSize = 12.sp)
                                    Text("${settings.smoothPanSpeed}% (Cinematic Fluid)", color = Cyan400, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                                Slider(
                                    value = settings.smoothPanSpeed.toFloat(),
                                    onValueChange = { onSettingsChange(settings.copy(smoothPanSpeed = it.toInt())) },
                                    valueRange = 0f..100f,
                                    colors = SliderDefaults.colors(
                                        thumbColor = Cyan400,
                                        activeTrackColor = Cyan400,
                                        inactiveTrackColor = Zinc800
                                    )
                                )
                            }
                        }
                    }
                }

                // 3. Dynamic Animated Emojis Section ("add emoji")
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Zinc900),
                    border = BorderStroke(1.dp, BorderWhite05),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "DYNAMIC ANIMATED EMOJIS & CAPTIONS",
                                        color = TextMuted,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(text = "🔥 🚀 🤯", fontSize = 12.sp)
                                }
                                Text(
                                    text = "Generates trending auto-captions with bouncing reactive emojis",
                                    color = Zinc500,
                                    fontSize = 11.sp
                                )
                            }
                            Switch(
                                checked = settings.addDynamicEmojis,
                                onCheckedChange = { onSettingsChange(settings.copy(addDynamicEmojis = it)) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = AmberGold,
                                    uncheckedThumbColor = Zinc400,
                                    uncheckedTrackColor = Zinc800
                                )
                            )
                        }

                        if (settings.addDynamicEmojis) {
                            // Live Bouncing Subtitle & Emoji Preview Box
                            BouncingEmojiPreviewCard(style = settings.emojiAnimationStyle)

                            // Animation Style Selector
                            Text(
                                text = "EMOJI ANIMATION STYLE",
                                color = TextMuted,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )

                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(EmojiAnimationStyle.values()) { animStyle ->
                                    val isSel = settings.emojiAnimationStyle == animStyle
                                    Surface(
                                        color = if (isSel) AmberGold.copy(alpha = 0.2f) else Zinc950,
                                        shape = RoundedCornerShape(12.dp),
                                        border = BorderStroke(1.dp, if (isSel) AmberGold else BorderWhite05),
                                        modifier = Modifier.clickable {
                                            onSettingsChange(settings.copy(emojiAnimationStyle = animStyle))
                                        }
                                    ) {
                                        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(
                                                    text = animStyle.label,
                                                    color = if (isSel) AmberGold else TextPrimary,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(text = animStyle.sampleEmojis, fontSize = 11.sp)
                                            }
                                            Text(
                                                text = animStyle.description,
                                                color = Zinc500,
                                                fontSize = 9.sp
                                            )
                                        }
                                    }
                                }
                            }

                            // Emoji Density
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf("High Viral (Max Hook)", "Balanced Retention", "Punchlines Only").forEach { density ->
                                    val isSel = settings.emojiDensity.startsWith(density.take(4))
                                    Surface(
                                        color = if (isSel) Cyan400.copy(alpha = 0.15f) else Zinc950,
                                        shape = RoundedCornerShape(10.dp),
                                        border = BorderStroke(1.dp, if (isSel) Cyan400 else BorderWhite05),
                                        modifier = Modifier.weight(1f).clickable {
                                            onSettingsChange(settings.copy(emojiDensity = density))
                                        }
                                    ) {
                                        Text(
                                            text = density,
                                            color = if (isSel) Cyan400 else Zinc400,
                                            fontSize = 10.sp,
                                            fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.padding(vertical = 7.dp, horizontal = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // 4. Dedicated 6K Quality Showcase Card ("6k quility")
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Zinc900),
                    border = BorderStroke(1.dp, Brush.horizontalGradient(listOf(ElectricPurple, Cyan400))),
                    modifier = Modifier.fillMaxWidth().testTag("short_clip_6k_card")
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(Brush.linearGradient(listOf(ElectricPurple, Cyan500))),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Hd,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "6K Ultra HD Short Export",
                                            color = TextPrimary,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            color = ElectricPurple.copy(alpha = 0.2f),
                                            shape = RoundedCornerShape(6.dp),
                                            border = BorderStroke(1.dp, ElectricPurple.copy(alpha = 0.4f))
                                        ) {
                                            Text(
                                                text = "3456 × 6144",
                                                color = ElectricViolet,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Black,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = "Neural oversampling prevents TikTok/Reels compression artifacts",
                                        color = Zinc400,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }

                        // Quality Selection Toggle for Shorts: 1080p vs 4K vs 6K
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(
                                QualityOption.FHD_1080P to "1080p FHD",
                                QualityOption.UHD_4K to "4K UHD",
                                QualityOption.UHD_6K to "6K UHD (Best 🔥)"
                            ).forEach { (q, label) ->
                                val isSel = settings.targetShortQuality == q
                                Surface(
                                    color = if (isSel) Cyan400.copy(alpha = 0.2f) else Zinc950,
                                    shape = RoundedCornerShape(12.dp),
                                    border = BorderStroke(1.dp, if (isSel) Cyan400 else BorderWhite05),
                                    modifier = Modifier.weight(1f).clickable {
                                        onSettingsChange(settings.copy(targetShortQuality = q))
                                    }
                                ) {
                                    Column(
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = label,
                                            color = if (isSel) Cyan400 else TextSecondary,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = if (q == QualityOption.UHD_6K) "3456×6144" else q.resolutionLabel,
                                            color = Zinc500,
                                            fontSize = 9.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BouncingEmojiPreviewCard(style: EmojiAnimationStyle) {
    val infiniteTransition = rememberInfiniteTransition(label = "emoji_bounce")
    val bounceOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -12f,
        animationSpec = infiniteRepeatable(
            animation = tween(450, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounce"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(
                Brush.verticalGradient(
                    listOf(Zinc950, Color(0xFF13111C))
                )
            )
            .border(1.dp, BorderWhite10, RoundedCornerShape(14.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "LIVE DYNAMIC CAPTION PREVIEW",
                color = Zinc500,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = when (style) {
                        EmojiAnimationStyle.POP_BOUNCE -> "WAIT UNTIL YOU SEE THIS"
                        EmojiAnimationStyle.KINETIC_NEON -> "ULTRA 6K NEURAL RES"
                        EmojiAnimationStyle.MR_BEAST -> "I REMASTERED THIS IN 6K"
                        EmojiAnimationStyle.MINIMAL_LUXE -> "Cinematic Clarity Unlocked"
                    },
                    color = when (style) {
                        EmojiAnimationStyle.MR_BEAST -> Color(0xFFFFEB3B)
                        EmojiAnimationStyle.KINETIC_NEON -> Cyan400
                        else -> Color.White
                    },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = if (style == EmojiAnimationStyle.MR_BEAST) 0.5.sp else 0.sp
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = when (style) {
                        EmojiAnimationStyle.POP_BOUNCE -> "🤯 🔥"
                        EmojiAnimationStyle.KINETIC_NEON -> "⚡ 🚀"
                        EmojiAnimationStyle.MR_BEAST -> "👑 💥"
                        EmojiAnimationStyle.MINIMAL_LUXE -> "✨ 💎"
                    },
                    fontSize = 20.sp,
                    modifier = Modifier.offset(y = bounceOffset.dp)
                )
            }
        }
    }
}
