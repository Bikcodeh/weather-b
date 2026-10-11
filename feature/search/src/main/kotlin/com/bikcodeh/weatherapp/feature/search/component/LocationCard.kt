package com.bikcodeh.weatherapp.feature.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikcodeh.weatherapp.core.designsystem.theme.WeatherTheme
import com.bikcodeh.weatherapp.feature.search.model.LocationUiModel

/** Clickable search result showing the location name and its region/country. */
@Composable
internal fun LocationCard(
    location: LocationUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(text = location.name, style = MaterialTheme.typography.titleMedium)
            if (location.subtitle.isNotEmpty()) {
                Text(
                    text = location.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview
@Composable
private fun LocationCardPreview() {
    WeatherTheme {
        LocationCard(
            location = LocationUiModel(
                id = 1,
                name = "Yumbo",
                subtitle = "Valle del Cauca, Colombia",
            ),
            onClick = {},
        )
    }
}
