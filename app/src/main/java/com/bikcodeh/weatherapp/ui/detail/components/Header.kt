package com.bikcodeh.weatherapp.ui.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bikcodeh.weatherapp.ui.utils.toReadableDate

@Composable
fun WeatherDetailHeader(
    location: String,
    condition: String,
    temperature: Double,
    dateTime: String,
    weatherIcon: String,
    precipitation: Double,
    windSpeed: Long,
    humidity: Long,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Default.LocationOn,
                contentDescription = ""
            )
            Text(
                text = location,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White.copy(alpha = 0.85f)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = dateTime.toReadableDate(),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.7f)
        )

        Text(
            text = "$temperature°",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 100.sp,
            color = Color.White
        )

        Text(
            text = condition,
            style = MaterialTheme.typography.displaySmall,
            color = Color.White.copy(alpha = 0.85f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        AsyncImage(
            model = weatherIcon,
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        WeatherStatsRow(
            precipitation = precipitation,
            humidity = humidity,
            windSpeed = windSpeed,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Preview
@Composable
private fun WeatherDetailHeaderPreview() {
    WeatherDetailHeader(
        condition = "Sunny",
        temperature = 22.0,
        dateTime = "2025-12-09",
        weatherIcon = "",
        precipitation = 2.3,
        windSpeed = 20,
        humidity = 2,
        location = "Yumbo"
    )
}