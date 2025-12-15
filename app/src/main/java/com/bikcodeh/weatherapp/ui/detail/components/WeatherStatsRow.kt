package com.bikcodeh.weatherapp.ui.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bikcodeh.weatherapp.R

@Composable
fun WeatherStatsRow(
    precipitation: Double,
    humidity: Long,
    windSpeed: Long,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFEAF0FF),
                        Color(0xFFF5F8FF)
                    )
                )
            )
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        WeatherStatItem(
            iconRes = R.drawable.ic_umbrella,
            value = "$precipitation%",
            label = "Precipitation"
        )
        WeatherStatItem(
            iconRes = R.drawable.ic_water,
            value = "$humidity%",
            label = "Humidity"
        )
        WeatherStatItem(
            iconRes = R.drawable.ic_wind,
            value = "$windSpeed km/h",
            label = "Wind speed"
        )
    }
}
