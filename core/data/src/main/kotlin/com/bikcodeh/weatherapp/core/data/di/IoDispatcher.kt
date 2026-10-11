package com.bikcodeh.weatherapp.core.data.di

import javax.inject.Qualifier

/** Qualifies the [kotlinx.coroutines.CoroutineDispatcher] meant for blocking I/O work. */
@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class IoDispatcher
