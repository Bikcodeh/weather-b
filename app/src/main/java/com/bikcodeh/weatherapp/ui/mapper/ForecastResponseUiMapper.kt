package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.domain.model.WeatherForecast
import com.bikcodeh.weatherapp.ui.model.WeatherDetailUiModel

fun WeatherForecast.toUiModel(): WeatherDetailUiModel =
    WeatherDetailUiModel(
        current = current.toUiModel(location.name),
        forecastDays = forecast.map { it.toUiModel() }
    )
