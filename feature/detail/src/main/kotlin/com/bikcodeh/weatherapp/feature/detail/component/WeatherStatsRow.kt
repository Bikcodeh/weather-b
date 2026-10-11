package com.bikcodeh.weatherapp.feature.detail.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bikcodeh.weatherapp.core.designsystem.icon.WeatherIcons
import com.bikcodeh.weatherapp.feature.detail.R

/** Card with precipitation, humidity and wind speed. */
@Composable
internal fun WeatherStatsRow(
    precipitationMm: Double,
    humidityPercent: Int,
    windSpeedKph: Int,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Brush.horizontalGradient(listOf(colors.surfaceVariant, colors.surface)))
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        WeatherStat(
            iconRes = WeatherIcons.Precipitation,
            value = stringResource(R.string.feature_detail_precipitation_value, precipitationMm),
            label = stringResource(R.string.feature_detail_precipitation),
        )
        WeatherStat(
            iconRes = WeatherIcons.Humidity,
            value = stringResource(R.string.feature_detail_humidity_value, humidityPercent),
            label = stringResource(R.string.feature_detail_humidity),
        )
        WeatherStat(
            iconRes = WeatherIcons.Wind,
            value = stringResource(R.string.feature_detail_wind_value, windSpeedKph),
            label = stringResource(R.string.feature_detail_wind),
        )
    }
}

@Composable
private fun WeatherStat(
    @DrawableRes iconRes: Int,
    value: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    Column(
        // Read by screen readers as a single element, e.g. "40%, Humidity".
        modifier = modifier.semantics(mergeDescendants = true) {},
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp),
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
