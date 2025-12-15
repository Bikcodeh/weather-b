package com.bikcodeh.weatherapp.ui.model

data class ForecastDayUiModel(
    val date: String,
    val maxTemperatureCelsius: Double?,
    val minTemperatureCelsius: Double?,
    val condition: String?,
    val weatherIcon: String?,
)