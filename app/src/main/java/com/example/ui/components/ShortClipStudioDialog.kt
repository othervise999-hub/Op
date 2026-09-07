package com.example.ui.components

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.CropPortrait
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Hd
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.model.FramingRatio
import com.example.model.QualityOption
import com.example.model.ShortClipAnalyzer
import com.example.model.ShortClipSettings
import com.example.model.VideoMetadata
import com.example.ui.theme.AmberGold
import com.example.ui.theme.BorderWhite10
import com.example.ui.theme.BorderWhite05
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Cyan500
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.RadiantPink
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.Zinc400
import com.example.ui.theme.Zinc500
import com.example.ui.theme.Zinc900
import com.example.ui.theme.Zinc950

@Composable
fun ShortClipStudioDialog(
    metadata: VideoMetadata,
    settings: ShortClipSettings,
    onSettingsChange: (ShortClipSettings) -> Unit,
    onApplyAndExport: () -> Unit,
    onDismiss: () -> Unit,
    previewBitmap: Bitmap? = null
) {
    val context = LocalContext.current
    val viralSegments = remember(metadata) {
        ShortClipAnalyzer.generateViralSegmentsForMedia(metadata)
    }

    val selectedSegment = viralSegments.find { it.id == settings.selectedSegmentId } ?: viralSegments.first()

    // Animation for smooth camera reframing ("edit smoothly unique part")
    val infiniteTransition = rememberInfiniteTransition(label = "studio_anim")
    val smoothCameraPan by infiniteTransition.animateFloat(
        initialValue = -18f,
        targetValue = 18f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "smooth_pan"
    )

    // Animated dynamic emoji bounce
    val emojiBounce by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -10f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "emoji_bounce"
    )

    // Progress timeline playback loop
    val timelineProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(selectedSegment.durationSeconds() * 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "timeline"
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianDark),
            border = BorderStroke(1.dp, BorderWhite10),
            modifier = Modifier
                .fillMaxWidth(0.96f)
                .fillMaxHeight(0.92f)
                .testTag("short_clip_studio_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(Brush.linearGradient(listOf(RadiantPink, Cyan400))),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCut,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "AI Short Clip Studio",
                                    color = TextPrimary,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    color = ElectricPurple.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(6.dp),
                                    border = BorderStroke(1.dp, ElectricPurple.copy(alpha = 0.4f))
                                ) {
                                    Text(
                                        text = "6K 60FPS",
                                        color = ElectricViolet,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Black,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Text(
                                text = "Auto-reframed • Animated emojis • 3456×6144 Super-Res",
                                color = Cyan400,
                                fontSize = 11.sp
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Zinc400
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Interactive 9:16 Vertical Phone Mockup Screen
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(220.dp)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(26.dp))
                            .background(Color.Black)
                            .border(2.dp, Brush.verticalGradient(listOf(Cyan400, RadiantPink)), RoundedCornerShape(26.dp))
                    ) {
                        // Background Video / Photo Frame
                        if (previewBitmap != null) {
                            Image(
                                bitmap = previewBitmap.asImageBitmap(),
                                contentDescription = "Short Clip Frame",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .offset(x = if (settings.smoothFocalTracking) smoothCameraPan.dp else 0.dp)
                            )
                        } else {
                            Image(
                                painter = painterResource(id = metadata.thumbnailDrawableId ?: R.drawable.sample_cyberpunk),
                                contentDescription = "Sample",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .offset(x = if (settings.smoothFocalTracking) smoothCameraPan.dp else 0.dp)
                            )
                        }

                        // Gradient Shadow for Subtitles
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.Black.copy(alpha = 0.35f),
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.85f)
                                        )
                                    )
                                )
                        )

                        // Top Badges (Viral Score & 6K Tag)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = AmberGold.copy(alpha = 0.85f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = "${selectedSegment.viralScore}% VIRAL",
                                        color = Color.Black,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(text = selectedSegment.emojiTag, fontSize = 10.sp)
                                }
                            }

                            Surface(
                                color = ElectricPurple.copy(alpha = 0.85f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "6K UHD",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        }

                        // Bottom Dynamic Subtitle with Animated Bouncing Emojis ("add emoji")
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(horizontal = 10.dp, vertical = 18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (settings.addDynamicEmojis) {
                                Text(
                                    text = when (selectedSegment.id) {
                                        "hook_1" -> "🤯 🔥"
                                        "hook_2" -> "🚀 ⚡"
                                        "hook_3" -> "👀 💥"
                                        else -> "💡 🎯"
                                    },
                                    fontSize = 24.sp,
                                    modifier = Modifier.offset(y = emojiBounce.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }

                            Text(
                                text = selectedSegment.hookCaption,
                                color = Color(0xFFFFEB3B),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center,
                                lineHeight = 15.sp,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Black.copy(alpha = 0.6f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Playback Timeline Bar
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(3.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.3f))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(timelineProgress)
                                        .height(3.dp)
                                        .clip(CircleShape)
                                        .background(Brush.horizontalGradient(listOf(Cyan400, RadiantPink)))
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Viral Segments Selector Chips
                Text(
                    text = "SELECT DETECTED VIRAL HOOK",
                    color = TextMuted,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    viralSegments.forEach { seg ->
                        val isSel = seg.id == selectedSegment.id
                        Surface(
                            color = if (isSel) Cyan400.copy(alpha = 0.2f) else Zinc900,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, if (isSel) Cyan400 else BorderWhite05),
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    onSettingsChange(
                                        settings.copy(
                                            selectedSegmentId = seg.id,
                                            customStartMs = seg.startMs,
                                            customEndMs = seg.endMs
                                        )
                                    )
                                }
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = seg.emojiTag, fontSize = 16.sp)
                                Text(
                                    text = "${seg.viralScore}%",
                                    color = if (isSel) Cyan400 else TextPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = seg.durationFormatted(),
                                    color = Zinc500,
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Action Bar: Export 6K Short
                Button(
                    onClick = {
                        Toast.makeText(context, "Rendering 6K Short with dynamic emojis...", Toast.LENGTH_SHORT).show()
                        onApplyAndExport()
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Cyan500),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("export_6k_short_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Render & Export 6K Short (3456×6144)",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
