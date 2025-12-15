package com.bikcodeh.weatherapp.core.di

import com.bikcodeh.weatherapp.core.dispatcher.DispatcherProviderImpl
import com.bikcodeh.weatherapp.domain.commons.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider =
        DispatcherProviderImpl()
}