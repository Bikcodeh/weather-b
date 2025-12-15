package com.bikcodeh.weatherapp.ui.detail.viewmodel

import androidx.lifecycle.viewModelScope
import com.bikcodeh.weatherapp.core.mvi.MVIViewModel
import com.bikcodeh.weatherapp.domain.commons.DispatcherProvider
import com.bikcodeh.weatherapp.domain.error.toFailure
import com.bikcodeh.weatherapp.domain.repository.WeatherRepository
import com.bikcodeh.weatherapp.ui.error.toMessageRes
import com.bikcodeh.weatherapp.ui.mapper.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: DispatcherProvider
) : MVIViewModel<DetailState, DetailEffect, DetailEvent>(dispatcher) {

    override val initialState: DetailState
        get() = DetailState(isLoading = true)

    override fun handleEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.LoadWeather -> loadWeather(event.query)
        }
    }

    private fun loadWeather(query: String) {
        viewModelScope.launch(dispatcher.io) {

            setState { copy(isLoading = true, isError = false) }

            weatherRepository.getForecast(query, days = "3")
                .onSuccess { response ->
                    val modelUi = response.toUiModel()
                    setState {
                        copy(
                            current = modelUi.current,
                            forecast = modelUi.forecastDays,
                            isLoading = false,
                            isError = false
                        )
                    }
                }
                .onFailure { throwable ->
                    Timber.e(throwable)
                    setState { copy(isLoading = false, isError = true) }

                    val failure = throwable.toFailure()

                    sendEffect(
                        DetailEffect.ShowErrorMessage(
                            failure.toMessageRes()
                        )
                    )
                }
        }
    }
}
