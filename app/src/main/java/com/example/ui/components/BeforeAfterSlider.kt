package com.example.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BorderWhite10
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Cyan500
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.Zinc900
import kotlin.math.roundToInt

@Composable
fun BeforeAfterSlider(
    originalBitmap: Bitmap?,
    enhancedBitmap: Bitmap?,
    modifier: Modifier = Modifier,
    enhancedQualityLabel: String = "4K AI"
) {
    var splitPosition by remember { mutableFloatStateOf(0.5f) }

    BoxWithConstraints(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .border(1.dp, BorderWhite10, RoundedCornerShape(24.dp))
            .background(Zinc900)
            .clipToBounds()
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    change.consume()
                    val newPos = (change.position.x / size.width.toFloat()).coerceIn(0.05f, 0.95f)
                    splitPosition = newPos
                }
            }
            .testTag("before_after_slider_container")
    ) {
        if (originalBitmap != null && enhancedBitmap != null) {
            val originalImage = remember(originalBitmap) { originalBitmap.asImageBitmap() }
            val enhancedImage = remember(enhancedBitmap) { enhancedBitmap.asImageBitmap() }

            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val splitX = canvasWidth * splitPosition

                // 1. Draw enhanced (right/full background)
                drawImage(
                    image = enhancedImage,
                    dstSize = androidx.compose.ui.unit.IntSize(canvasWidth.toInt(), canvasHeight.toInt())
                )

                // 2. Draw original clipped to the left side
                val leftClip = Path().apply {
                    addRect(Rect(0f, 0f, splitX, canvasHeight))
                }
                clipPath(leftClip) {
                    drawImage(
                        image = originalImage,
                        dstSize = androidx.compose.ui.unit.IntSize(canvasWidth.toInt(), canvasHeight.toInt())
                    )
                }

                // 3. Draw vertical divider bar with glowing cyan accent (Sophisticated Dark)
                drawLine(
                    color = Cyan400.copy(alpha = 0.3f),
                    start = Offset(splitX, 0f),
                    end = Offset(splitX, canvasHeight),
                    strokeWidth = 8.dp.toPx()
                )
                drawLine(
                    color = Cyan400,
                    start = Offset(splitX, 0f),
                    end = Offset(splitX, canvasHeight),
                    strokeWidth = 2.dp.toPx()
                )
            }
        }

        // Before label (Bottom Left)
        Surface(
            color = Color.Black.copy(alpha = 0.6f),
            shape = RoundedCornerShape(6.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderWhite10),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(14.dp)
        ) {
            Text(
                text = "ORIGINAL",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.5.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        // After label (Bottom Right)
        Surface(
            color = Cyan500.copy(alpha = 0.85f),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(14.dp)
        ) {
            Text(
                text = "AI ENHANCED",
                color = Color.Black,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        // Draggable Handle in the center: cyan-400 circle with dark border & black symbol
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset {
                        IntOffset(
                            x = (this@BoxWithConstraints.maxWidth.toPx() * splitPosition - 18.dp.toPx()).roundToInt(),
                            y = 0
                        )
                    }
                    .size(36.dp)
                    .shadow(12.dp, CircleShape, spotColor = Cyan400)
                    .border(3.5.dp, ObsidianDark, CircleShape)
                    .background(Cyan400, CircleShape)
                    .testTag("slider_drag_handle"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "< >",
                    color = Color.Black,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-0.5).sp
                )
            }
        }
    }
}
