package com.bikcodeh.weatherapp.ui.search.viewmodel

import androidx.lifecycle.viewModelScope
import com.bikcodeh.weatherapp.core.mvi.MVIViewModel
import com.bikcodeh.weatherapp.domain.commons.DispatcherProvider
import com.bikcodeh.weatherapp.domain.error.toFailure
import com.bikcodeh.weatherapp.domain.repository.WeatherRepository
import com.bikcodeh.weatherapp.ui.error.toMessageRes
import com.bikcodeh.weatherapp.ui.mapper.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: DispatcherProvider
) : MVIViewModel<SearchState, SearchEffect, SearchEvent>(dispatcher) {

    private var searchJob: Job? = null

    override val initialState: SearchState
        get() = SearchState()

    override fun handleEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryChanged -> {
                setState {
                    copy(query = event.query)
                }
                getLocations(event.query)
            }

            is SearchEvent.OnLocationSelected -> sendEffect(SearchEffect.Navigate(event.query))
        }
    }

    private fun getLocations(query: String) {

        if (query.isBlank()) {
            setState {
                copy(
                    locations = emptyList(),
                    isLoading = false,
                    isError = false
                )
            }
            return
        }

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            setState {
                copy(
                    isLoading = true,
                    isError = false
                )
            }

            delay(400)

            weatherRepository.getSearch(query)
                .onSuccess { data ->
                    setState {
                        copy(
                            locations = data.map { it.toUiModel() },
                            isLoading = false,
                            isError = false
                        )
                    }
                }
                .onFailure { throwable ->
                    Timber.e(throwable)

                    setState {
                        copy(isLoading = false, isError = true)
                    }

                    val failure = throwable.toFailure()
                    sendEffect(
                        SearchEffect.ShowErrorMessage(
                            failure.toMessageRes()
                        )
                    )
                }
        }
    }
}