package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.data.remote.dto.ForecastResponseDto
import com.bikcodeh.weatherapp.ui.model.WeatherDetailUiModel

fun ForecastResponseDto.toUiModel(): WeatherDetailUiModel =
    WeatherDetailUiModel(
        current = current.toUiModel(location.name),
        forecastDays = forecast.forecastday.map { it.toUiModel() }
    )