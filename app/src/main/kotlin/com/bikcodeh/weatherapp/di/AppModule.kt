package com.bikcodeh.weatherapp.di

import com.bikcodeh.weatherapp.BuildConfig
import com.bikcodeh.weatherapp.core.data.WeatherApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** App-level configuration consumed by the library modules (composition root). */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApiConfig(): WeatherApiConfig = WeatherApiConfig(
        apiKey = BuildConfig.WEATHER_API_KEY,
        enableHttpLogging = BuildConfig.DEBUG,
    )
}
