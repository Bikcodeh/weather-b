package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.domain.model.CurrentWeather
import com.bikcodeh.weatherapp.ui.model.CurrentWeatherUiModel

fun CurrentWeather.toUiModel(location: String): CurrentWeatherUiModel =
    CurrentWeatherUiModel(
        location = location,
        temperatureCelsius = tempC,
        feelsLikeCelsius = feelslikeC,
        humidityPercentage = humidity,
        windSpeedKph = windKph,
        windDirection = windDir,
        precipitationMm = precipMm,
        visibilityKm = visKm.toLong(),
        weatherConditionText = condition.text,
        weatherConditionIconUrl = condition.icon
    )
