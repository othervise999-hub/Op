package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = ObsidianDark,
    primaryContainer = ObsidianElevated,
    onPrimaryContainer = NeonCyan,
    secondary = ElectricPurple,
    onSecondary = TextPrimary,
    secondaryContainer = ObsidianSurface,
    onSecondaryContainer = ElectricViolet,
    tertiary = RadiantPink,
    background = ObsidianDark,
    onBackground = TextPrimary,
    surface = ObsidianSurface,
    onSurface = TextPrimary,
    surfaceVariant = ObsidianElevated,
    onSurfaceVariant = TextSecondary,
    outline = ObsidianBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Force cinematic dark UI by default for professional video editing
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
