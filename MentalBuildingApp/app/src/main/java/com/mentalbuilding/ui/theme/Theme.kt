package com.mentalbuilding.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = PrimaryBlueLight,
    onPrimaryContainer = TextPrimary,

    secondary = PrimaryBlueDark,
    onSecondary = Color.White,
    secondaryContainer = PrimaryBlueLight,
    onSecondaryContainer = TextPrimary,

    background = BackgroundWhite,
    onBackground = TextPrimary,

    surface = SurfaceWhite,
    onSurface = TextPrimary,

    error = ErrorRed,
    onError = Color.White,

    outline = BorderGray,
    outlineVariant = DividerGray
)

@Composable
fun MentalBuildingTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
