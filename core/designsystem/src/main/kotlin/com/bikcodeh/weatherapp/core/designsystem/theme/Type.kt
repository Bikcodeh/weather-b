package com.bikcodeh.weatherapp.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/** Default Material 3 type scale. */
internal val WeatherTypography = Typography()

/** Style of the current temperature, the most prominent text of the app. */
val TemperatureTextStyle = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 96.sp,
    lineHeight = 104.sp,
)
