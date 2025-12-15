package com.bikcodeh.weatherapp.ui.search.viewmodel

import androidx.annotation.StringRes
import com.bikcodeh.weatherapp.core.mvi.ViewEvent
import com.bikcodeh.weatherapp.core.mvi.ViewSideEffect
import com.bikcodeh.weatherapp.core.mvi.ViewState
import com.bikcodeh.weatherapp.ui.model.LocationUiModel

sealed class SearchEffect : ViewSideEffect {
    data class ShowErrorMessage(@StringRes val errorMessage: Int) : SearchEffect()
    data class Navigate(val query: String) : SearchEffect()
}

sealed class SearchEvent : ViewEvent {
    data class OnQueryChanged(val query: String) : SearchEvent()
    data class OnLocationSelected(val query: String): SearchEvent()
}

data class SearchState(
    val query: String = "",
    val locations: List<LocationUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
) : ViewState