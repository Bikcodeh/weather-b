package com.bikcodeh.weatherapp.ui.components

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun SetStatusBar(
    color: Color,
    darkIcons: Boolean
) {
    val context = LocalContext.current

    SideEffect {
        val window = (context as Activity).window
        window.statusBarColor = color.toArgb()

        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).isAppearanceLightStatusBars = darkIcons
    }
}