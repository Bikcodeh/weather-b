package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.data.remote.dto.ForecastDayDto
import com.bikcodeh.weatherapp.ui.model.ForecastDayUiModel

fun ForecastDayDto.toUiModel(): ForecastDayUiModel =
    ForecastDayUiModel(
        date = date,
        maxTemperatureCelsius = day.maxtempC,
        minTemperatureCelsius = day.mintempC,
        weatherIcon = day.condition?.icon?.let { "https:$it" },
        condition = day.condition?.text
    )