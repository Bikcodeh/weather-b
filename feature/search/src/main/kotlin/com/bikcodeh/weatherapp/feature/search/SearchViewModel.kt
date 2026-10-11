package com.bikcodeh.weatherapp.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcodeh.weatherapp.core.domain.model.Location
import com.bikcodeh.weatherapp.core.domain.result.fold
import com.bikcodeh.weatherapp.core.domain.usecase.SearchLocationsUseCase
import com.bikcodeh.weatherapp.core.ui.error.messageRes
import com.bikcodeh.weatherapp.feature.search.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

/**
 * Turns the text typed by the user into [SearchUiState]s.
 *
 * The pipeline is reactive: queries are trimmed and debounced, duplicates are skipped and
 * `flatMapLatest` cancels an in-flight search as soon as a newer query (or a retry) arrives, so
 * stale results can never overwrite fresh ones.
 */
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val searchLocations: SearchLocationsUseCase,
) : ViewModel() {

    private val query = MutableStateFlow("")
    private val retryRequests = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    val uiState: StateFlow<SearchUiState> = combine(
        query
            .map(String::trim)
            .debounce { query -> if (query.isSearchable()) SEARCH_DEBOUNCE_MILLIS else 0L }
            .distinctUntilChanged(),
        retryRequests.onStart { emit(Unit) },
    ) { query, _ -> query }
        .flatMapLatest(::searchResults)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = SearchUiState.Idle,
        )

    /** Called on every change of the search field. */
    fun onQueryChange(query: String) {
        this.query.value = query
    }

    /** Repeats the last search, e.g. after a network error. */
    fun onRetry() {
        retryRequests.tryEmit(Unit)
    }

    private fun searchResults(query: String): Flow<SearchUiState> {
        if (!query.isSearchable()) return flowOf(SearchUiState.Idle)
        return flow {
            emit(SearchUiState.Loading)
            val uiState = searchLocations(query).fold(
                onSuccess = { locations -> locations.toUiState() },
                onFailure = { error -> SearchUiState.Error(error.messageRes()) },
            )
            emit(uiState)
        }
    }

    private fun List<Location>.toUiState(): SearchUiState =
        if (isEmpty()) SearchUiState.Empty else SearchUiState.Success(map(Location::toUiModel))

    private fun String.isSearchable(): Boolean = length >= SearchLocationsUseCase.MIN_QUERY_LENGTH

    private companion object {
        const val SEARCH_DEBOUNCE_MILLIS = 300L
        const val STOP_TIMEOUT_MILLIS = 5_000L
    }
}
