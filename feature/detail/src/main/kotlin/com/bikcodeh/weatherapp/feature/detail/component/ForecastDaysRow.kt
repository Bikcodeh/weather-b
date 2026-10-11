package com.bikcodeh.weatherapp.feature.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bikcodeh.weatherapp.core.designsystem.component.NetworkImage
import com.bikcodeh.weatherapp.feature.detail.R
import com.bikcodeh.weatherapp.feature.detail.model.ForecastDayUiModel
import com.bikcodeh.weatherapp.feature.detail.util.toShortDayText

/** Horizontal list of the upcoming days; centered when it fits, scrollable when it does not. */
@Composable
internal fun ForecastDaysRow(days: List<ForecastDayUiModel>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
    ) {
        items(items = days, key = { day -> day.date.toEpochDay() }) { day ->
            ForecastDayCard(day = day)
        }
    }
}

@Composable
private fun ForecastDayCard(day: ForecastDayUiModel, modifier: Modifier = Modifier) {
    val locale = LocalConfiguration.current.locales[0]

    Card(
        modifier = modifier.width(96.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = remember(day.date, locale) { day.date.toShortDayText(locale) },
                style = MaterialTheme.typography.labelLarge,
            )
            NetworkImage(
                url = day.iconUrl,
                contentDescription = day.condition,
                modifier = Modifier.size(48.dp),
            )
            HighLowTemperatures(
                maxCelsius = day.maxTemperatureCelsius,
                minCelsius = day.minTemperatureCelsius,
            )
        }
    }
}

/** Max and min temperatures, announced by screen readers as "High 22°, low 10°". */
@Composable
private fun HighLowTemperatures(
    maxCelsius: Int,
    minCelsius: Int,
    modifier: Modifier = Modifier,
) {
    val description = stringResource(R.string.feature_detail_high_low, maxCelsius, minCelsius)
    Column(
        modifier = modifier.clearAndSetSemantics { contentDescription = description },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.feature_detail_temperature, maxCelsius),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = stringResource(R.string.feature_detail_temperature, minCelsius),
            style = MaterialTheme.typography.bodyMedium,
            color = LocalContentColor.current.copy(alpha = 0.7f),
        )
    }
}
