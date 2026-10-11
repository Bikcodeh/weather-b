package com.bikcodeh.weatherapp.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcodeh.weatherapp.core.domain.result.fold
import com.bikcodeh.weatherapp.core.domain.usecase.GetWeatherForecastUseCase
import com.bikcodeh.weatherapp.core.ui.error.messageRes
import com.bikcodeh.weatherapp.feature.detail.model.toUiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest

/**
 * Loads the weather of [locationId] and exposes it as [DetailUiState].
 *
 * The location id arrives through assisted injection, so the ViewModel knows nothing about
 * navigation. Loading starts lazily when the UI subscribes (no work in `init`) and restarts on
 * every [onRetry]; `transformLatest` cancels a load that is still running.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel(assistedFactory = DetailViewModel.Factory::class)
internal class DetailViewModel @AssistedInject constructor(
    @Assisted private val locationId: Long,
    private val getWeatherForecast: GetWeatherForecastUseCase,
) : ViewModel() {

    private val retryRequests = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    val uiState: StateFlow<DetailUiState> = retryRequests
        .onStart { emit(Unit) }
        .transformLatest {
            emit(DetailUiState.Loading)
            val uiState = getWeatherForecast(locationId).fold(
                onSuccess = { forecast -> DetailUiState.Success(forecast.toUiModel()) },
                onFailure = { error -> DetailUiState.Error(error.messageRes()) },
            )
            emit(uiState)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = DetailUiState.Loading,
        )

    /** Loads the weather again, e.g. after an error. */
    fun onRetry() {
        retryRequests.tryEmit(Unit)
    }

    @AssistedFactory
    interface Factory {
        fun create(locationId: Long): DetailViewModel
    }

    private companion object {
        const val STOP_TIMEOUT_MILLIS = 5_000L
    }
}
