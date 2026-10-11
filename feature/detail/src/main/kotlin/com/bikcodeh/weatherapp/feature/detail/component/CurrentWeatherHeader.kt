package com.bikcodeh.weatherapp.feature.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bikcodeh.weatherapp.core.designsystem.component.NetworkImage
import com.bikcodeh.weatherapp.core.designsystem.icon.WeatherIcons
import com.bikcodeh.weatherapp.core.designsystem.theme.TemperatureTextStyle
import com.bikcodeh.weatherapp.feature.detail.R
import com.bikcodeh.weatherapp.feature.detail.model.WeatherDetailUiModel
import com.bikcodeh.weatherapp.feature.detail.util.toFullDateText

/** Location, date, current temperature and condition. */
@Composable
internal fun CurrentWeatherHeader(weather: WeatherDetailUiModel, modifier: Modifier = Modifier) {
    val locale = LocalConfiguration.current.locales[0]
    val secondaryColor = LocalContentColor.current.copy(alpha = 0.75f)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                painter = painterResource(WeatherIcons.Location),
                contentDescription = null,
                modifier = Modifier.size(28.dp),
            )
            Text(
                text = weather.locationName,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
            )
        }
        weather.date?.let { date ->
            Text(
                text = remember(date, locale) { date.toFullDateText(locale) },
                style = MaterialTheme.typography.bodyLarge,
                color = secondaryColor,
            )
        }
        Text(
            text = stringResource(R.string.feature_detail_temperature, weather.temperatureCelsius),
            style = TemperatureTextStyle,
        )
        Text(
            text = weather.condition,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
        )
        Text(
            text = stringResource(R.string.feature_detail_feels_like, weather.feelsLikeCelsius),
            style = MaterialTheme.typography.bodyLarge,
            color = secondaryColor,
        )
        // Decorative: the condition is already announced by the text above.
        NetworkImage(
            url = weather.conditionIconUrl,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
        )
    }
}
