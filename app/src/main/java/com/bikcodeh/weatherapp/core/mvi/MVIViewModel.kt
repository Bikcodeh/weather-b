package com.bikcodeh.weatherapp.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcodeh.weatherapp.domain.commons.DispatcherProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class MVIViewModel<
        State: ViewState,
        Effect : ViewSideEffect,
        Event : ViewEvent
        >(
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    protected abstract val initialState: State

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    private val effectChannel = Channel<Effect>(Channel.BUFFERED)

    val effects = effectChannel.receiveAsFlow()

    protected abstract fun handleEvent(event: Event)

    protected fun setState(reducer: State.() -> State) {
        _state.update { it.reducer() }
    }

    fun sendEvent(event: Event) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            effectChannel.send(effect)
        }
    }

    init {
        viewModelScope.launch {
            eventChannel
                .receiveAsFlow()
                .collect { event ->
                    handleEvent(event)
                }
        }
    }
}