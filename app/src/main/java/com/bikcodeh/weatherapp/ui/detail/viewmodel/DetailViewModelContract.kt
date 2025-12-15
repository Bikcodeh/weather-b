package com.bikcodeh.weatherapp.ui.detail.viewmodel

import androidx.annotation.StringRes
import com.bikcodeh.weatherapp.core.mvi.ViewEvent
import com.bikcodeh.weatherapp.core.mvi.ViewSideEffect
import com.bikcodeh.weatherapp.core.mvi.ViewState
import com.bikcodeh.weatherapp.ui.model.CurrentWeatherUiModel
import com.bikcodeh.weatherapp.ui.model.ForecastDayUiModel

sealed class DetailEffect : ViewSideEffect {
    data class ShowErrorMessage(@StringRes val errorMessage: Int) : DetailEffect()
}

sealed class DetailEvent : ViewEvent {
    data class LoadWeather(val query: String) : DetailEvent()
}

data class DetailState(
    val current: CurrentWeatherUiModel? = null,
    val forecast: List<ForecastDayUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
) : ViewState