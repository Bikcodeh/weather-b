package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.data.remote.dto.CurrentWeatherDto
import com.bikcodeh.weatherapp.ui.model.CurrentWeatherUiModel

fun CurrentWeatherDto.toUiModel(location: String): CurrentWeatherUiModel =
    CurrentWeatherUiModel(
        location = location,
        temperatureCelsius = tempC ?: 0.0,
        feelsLikeCelsius = feelslikeC ?: tempC ?: 0.0,
        humidityPercentage = humidity ?: 0,
        windSpeedKph = windKph ?: 0.0,
        windDirection = windDir.orEmpty(),
        precipitationMm = precipMm ?: 0.0,
        visibilityKm = visKm?.toLong() ?: 0L,
        weatherConditionText = condition?.text.orEmpty(),
        weatherConditionIconUrl = condition?.icon.orEmpty()
    )
