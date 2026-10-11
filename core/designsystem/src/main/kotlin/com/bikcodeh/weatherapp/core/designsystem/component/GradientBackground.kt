package com.bikcodeh.weatherapp.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.bikcodeh.weatherapp.core.designsystem.theme.NightBlue
import com.bikcodeh.weatherapp.core.designsystem.theme.OceanBlue
import com.bikcodeh.weatherapp.core.designsystem.theme.SkyBlue

private val BackgroundBrush = Brush.verticalGradient(listOf(NightBlue, OceanBlue, SkyBlue))

/** Full-size vertical blue gradient drawn behind every screen, system bars included. */
@Composable
fun GradientBackground(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundBrush),
        content = content,
    )
}
