package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.domain.model.ForecastDay
import com.bikcodeh.weatherapp.ui.model.ForecastDayUiModel

fun ForecastDay.toUiModel(): ForecastDayUiModel =
    ForecastDayUiModel(
        date = date,
        maxTemperatureCelsius = maxTempC,
        minTemperatureCelsius = minTempC,
        weatherIcon = "https:${condition.icon}",
        condition = condition.text
    )
