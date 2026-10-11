package com.bikcodeh.weatherapp.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Color roles of the app:
 * - `background`/`onBackground`: the blue gradient behind every screen and the content on it.
 * - `surface`/`onSurface`: light cards placed over the gradient.
 * - `primaryContainer`: dark blue cards (forecast days).
 * - `primary`: accent for buttons and highlighted icons.
 */
private val WeatherColorScheme = lightColorScheme(
    primary = ElectricBlue,
    onPrimary = Color.White,
    primaryContainer = DeepBlue,
    onPrimaryContainer = Color.White,
    background = NightBlue,
    onBackground = Color.White,
    surface = CloudWhite,
    onSurface = Ink,
    surfaceVariant = MistBlue,
    onSurfaceVariant = Slate,
    error = Coral,
    onError = DarkCoral,
)

/**
 * Theme of the app.
 *
 * The brand look (white content over a blue gradient) is the same in light and dark system
 * modes, so a single color scheme is used and dynamic color is intentionally disabled: wallpaper
 * based colors would clash with the gradient.
 */
@Composable
fun WeatherTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = WeatherColorScheme,
        typography = WeatherTypography,
        content = content,
    )
}
