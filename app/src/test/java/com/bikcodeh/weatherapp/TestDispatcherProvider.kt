package com.bikcodeh.weatherapp

import com.bikcodeh.weatherapp.domain.commons.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher

class TestDispatcherProvider(
    private val dispatcher: CoroutineDispatcher
) : DispatcherProvider {

    override val io: CoroutineDispatcher = dispatcher
    override val main: CoroutineDispatcher = dispatcher
    override val default: CoroutineDispatcher = dispatcher
}
