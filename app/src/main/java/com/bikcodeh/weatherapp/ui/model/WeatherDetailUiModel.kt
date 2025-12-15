package com.bikcodeh.weatherapp.ui.model

data class WeatherDetailUiModel(
    val current: CurrentWeatherUiModel,
    val forecastDays: List<ForecastDayUiModel>
)