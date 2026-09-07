package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.CameraRoll
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Flare
import androidx.compose.material.icons.filled.HdrOn
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MovieFilter
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FilterType
import com.example.ui.theme.AmberGold
import com.example.ui.theme.BorderWhite05
import com.example.ui.theme.BorderWhite10
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Cyan500
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.Zinc300
import com.example.ui.theme.Zinc400
import com.example.ui.theme.Zinc500
import com.example.ui.theme.Zinc800
import com.example.ui.theme.Zinc900
import com.example.ui.theme.Zinc950

/**
 * Return an expressive, cinematic Material icon for each filter style.
 */
fun getFilterStyleIcon(filter: FilterType): ImageVector = when (filter) {
    FilterType.CINEMATIC -> Icons.Default.MovieFilter
    FilterType.HDR -> Icons.Default.HdrOn
    FilterType.VIBRANT -> Icons.Default.Palette
    FilterType.NATURAL -> Icons.Default.Landscape
    FilterType.WARM -> Icons.Default.WbSunny
    FilterType.COOL -> Icons.Default.AcUnit
    FilterType.BLACK_AND_WHITE -> Icons.Default.Contrast
    FilterType.VINTAGE -> Icons.Default.CameraRoll
    FilterType.FILM -> Icons.Default.Videocam
    FilterType.NIGHT -> Icons.Default.DarkMode
    FilterType.DRAMATIC -> Icons.Default.FlashOn
    FilterType.PORTRAIT -> Icons.Default.Face
    FilterType.ANIME -> Icons.Default.Brush
    FilterType.SHARP -> Icons.Default.Details
    FilterType.LUXURY -> Icons.Default.Diamond
    FilterType.GOLDEN -> Icons.Default.Flare
    FilterType.DARK -> Icons.Default.NightsStay
}

/**
 * Short cinematic genre/flavor tag for display under the title.
 */
fun getFilterStyleTagline(filter: FilterType): String = when (filter) {
    FilterType.CINEMATIC -> "Teal & Orange"
    FilterType.HDR -> "Dynamic HDR"
    FilterType.VIBRANT -> "Punchy Color"
    FilterType.NATURAL -> "True Tone"
    FilterType.WARM -> "Golden Glow"
    FilterType.COOL -> "Nordic Frost"
    FilterType.BLACK_AND_WHITE -> "Silver Noir"
    FilterType.VINTAGE -> "70s Analog"
    FilterType.FILM -> "35mm Cinema"
    FilterType.NIGHT -> "Noir Blue"
    FilterType.DRAMATIC -> "High Contrast"
    FilterType.PORTRAIT -> "Skin Radiance"
    FilterType.ANIME -> "Cel Shading"
    FilterType.SHARP -> "Micro Detail"
    FilterType.LUXURY -> "Champagne"
    FilterType.GOLDEN -> "Magic Hour"
    FilterType.DARK -> "Moody Noir"
}

/**
 * A sleek horizontal scrollable strip of filter icons that users can tap
 * to immediately apply cinematic styles to their video project.
 */
@Composable
fun CinematicFilterStrip(
    selectedFilter: FilterType,
    onSelectFilter: (FilterType) -> Unit,
    isPremium: Boolean,
    modifier: Modifier = Modifier,
    showHeader: Boolean = true
) {
    val listState = rememberLazyListState()
    val allFilters = FilterType.values()

    // Smoothly scroll to selected filter when it changes
    LaunchedEffect(selectedFilter) {
        val index = allFilters.indexOf(selectedFilter)
        if (index >= 0) {
            val targetIndex = (index - 1).coerceAtLeast(0)
            listState.animateScrollToItem(targetIndex)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("cinematic_filter_strip_container")
    ) {
        if (showHeader) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.MovieFilter,
                        contentDescription = null,
                        tint = Cyan400,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "CINEMATIC STYLES",
                        color = Zinc400,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                }

                // Active style badge
                Surface(
                    color = Cyan500.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Cyan400.copy(alpha = 0.35f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(Cyan400)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "${selectedFilter.title} • ${getFilterStyleTagline(selectedFilter)}",
                            color = Cyan400,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("cinematic_filter_strip")
        ) {
            items(allFilters, key = { it.name }) { filter ->
                val isSelected = filter == selectedFilter
                CinematicFilterIconItem(
                    filter = filter,
                    isSelected = isSelected,
                    isPremium = isPremium,
                    onClick = { onSelectFilter(filter) }
                )
            }
        }
    }
}

/**
 * Individual cinematic filter icon tile in the horizontal strip.
 */
@Composable
private fun CinematicFilterIconItem(
    filter: FilterType,
    isSelected: Boolean,
    isPremium: Boolean,
    onClick: () -> Unit
) {
    val styleColor = Color(filter.previewHexColor)
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Cyan400 else BorderWhite10,
        label = "filter_border_color"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isSelected) 1.08f else 1.0f,
        label = "filter_scale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(72.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp)
            .testTag("cinematic_filter_icon_${filter.name.lowercase()}")
    ) {
        // Filter Icon Frame
        Box(
            modifier = Modifier
                .size(60.dp)
                .scale(iconScale)
                .clip(RoundedCornerShape(18.dp))
                .border(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(18.dp)
                )
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            styleColor.copy(alpha = if (isSelected) 0.85f else 0.55f),
                            Zinc950
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            // Representative Style Icon
            Icon(
                imageVector = getFilterStyleIcon(filter),
                contentDescription = filter.title,
                tint = if (isSelected) Color.White else Zinc300,
                modifier = Modifier.size(26.dp)
            )

            // Top-Right Status Badge (Check for selected, Lock for Pro)
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(Cyan400),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.Black,
                        modifier = Modifier.size(11.dp)
                    )
                }
            } else if (filter.isPro && !isPremium) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(Zinc900.copy(alpha = 0.85f))
                        .border(1.dp, AmberGold.copy(alpha = 0.5f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Pro",
                        tint = AmberGold,
                        modifier = Modifier.size(9.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Filter Title
        Text(
            text = filter.title,
            color = if (isSelected) Cyan400 else TextPrimary,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

        // Filter Genre/Tagline
        Text(
            text = getFilterStyleTagline(filter),
            color = if (isSelected) Cyan400.copy(alpha = 0.8f) else Zinc500,
            fontSize = 9.sp,
            fontWeight = FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}
