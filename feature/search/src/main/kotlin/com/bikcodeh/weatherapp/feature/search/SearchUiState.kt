package com.bikcodeh.weatherapp.feature.search

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.bikcodeh.weatherapp.feature.search.model.LocationUiModel

/** Mutually exclusive states of the search results area. */
internal sealed interface SearchUiState {

    /** The query is too short to search: shows the welcome illustration. */
    data object Idle : SearchUiState

    data object Loading : SearchUiState

    /** The search finished without matches. */
    data object Empty : SearchUiState

    @Immutable
    data class Success(
        val locations: List<LocationUiModel>,
    ) : SearchUiState

    data class Error(
        @StringRes val messageRes: Int,
    ) : SearchUiState
}
