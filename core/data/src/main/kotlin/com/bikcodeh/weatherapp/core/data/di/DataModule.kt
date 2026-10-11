package com.bikcodeh.weatherapp.core.data.di

import com.bikcodeh.weatherapp.core.data.repository.DefaultWeatherRepository
import com.bikcodeh.weatherapp.core.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** Binds domain contracts to their data-layer implementations. */
@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    // Unscoped on purpose: the repository is stateless, so caching an instance buys nothing.
    @Binds
    abstract fun bindWeatherRepository(repository: DefaultWeatherRepository): WeatherRepository
}
