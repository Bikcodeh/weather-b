package com.bikcodeh.weatherapp.feature.detail

import androidx.annotation.StringRes
import com.bikcodeh.weatherapp.feature.detail.model.WeatherDetailUiModel

/** Mutually exclusive states of the detail screen. */
internal sealed interface DetailUiState {

    data object Loading : DetailUiState

    data class Success(
        val weather: WeatherDetailUiModel,
    ) : DetailUiState

    data class Error(
        @StringRes val messageRes: Int,
    ) : DetailUiState
}
