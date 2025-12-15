package com.bikcodeh.weatherapp.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikcodeh.weatherapp.R
import com.bikcodeh.weatherapp.ui.components.Loading
import com.bikcodeh.weatherapp.ui.components.LottieAnimation
import com.bikcodeh.weatherapp.ui.detail.components.ForecastRow
import com.bikcodeh.weatherapp.ui.detail.components.WeatherDetailHeader
import com.bikcodeh.weatherapp.ui.detail.viewmodel.DetailState
import com.bikcodeh.weatherapp.ui.model.CurrentWeatherUiModel
import com.bikcodeh.weatherapp.ui.model.ForecastDayUiModel

@Composable
fun DetailContent(state: DetailState) {

    val currentForecast = state.forecast.firstOrNull()
    val currentWeather = state.current

    if (state.isError) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            LottieAnimation(lottieFile = R.raw.error)
        }
    }

    if (state.isLoading) {
        Loading()
    } else {
        if (currentForecast == null || currentWeather == null) return

        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            WeatherDetailHeader(
                condition = currentForecast.condition!!,
                temperature = currentWeather.temperatureCelsius!!,
                location = currentWeather.location,
                dateTime = currentForecast.date,
                weatherIcon = currentForecast.weatherIcon!!,
                precipitation = currentWeather.precipitationMm,
                windSpeed = currentWeather.windSpeedKph.toLong(),
                humidity = currentWeather.humidityPercentage
            )

            Spacer(modifier = Modifier.height(30.dp))

            ForecastRow(
                forecast = state.forecast.drop(1).take(2),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF2A4E8C)
@Composable
private fun DetailContentPreview() {
    DetailContent(
        DetailState(
            current = CurrentWeatherUiModel(
                location = "atomorum",
                temperatureCelsius = 8.9,
                feelsLikeCelsius = 10.11,
                humidityPercentage = 9238,
                windSpeedKph = 12.13,
                windDirection = "ferri",
                precipitationMm = 14.15,
                visibilityKm = 8675,
                weatherConditionText = "vulputate",
                weatherConditionIconUrl = "https://duckduckgo.com/?q=faucibus"
            ),
            forecast = listOf(
                ForecastDayUiModel(
                    date = "2017-09-09",
                    maxTemperatureCelsius = 20.21,
                    minTemperatureCelsius = 22.23,
                    condition = "interdum",
                    weatherIcon = "explicari"
                )
            )
        )
    )
}
