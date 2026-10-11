package com.bikcodeh.weatherapp.core.data.di

import com.bikcodeh.weatherapp.core.data.WeatherApiConfig
import com.bikcodeh.weatherapp.core.data.remote.WeatherApi
import com.bikcodeh.weatherapp.core.data.remote.interceptor.ApiKeyInterceptor
import com.bikcodeh.weatherapp.core.data.remote.interceptor.LanguageInterceptor
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val TIMEOUT_SECONDS = 15L

    @Provides
    @Singleton
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideOkHttpClient(config: WeatherApiConfig): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(ApiKeyInterceptor(config.apiKey))
        .addInterceptor(LanguageInterceptor())
        .apply {
            if (config.enableHttpLogging) {
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                        redactQueryParams(ApiKeyInterceptor.API_KEY_QUERY_PARAMETER)
                    },
                )
            }
        }
        .build()

    /**
     * The OkHttp client is injected lazily so its (costly) creation happens on the first request,
     * which runs on a background dispatcher, instead of on the main thread during injection.
     */
    @Provides
    @Singleton
    fun provideWeatherApi(
        okHttpClient: Lazy<OkHttpClient>,
        json: Json,
        config: WeatherApiConfig,
    ): WeatherApi = Retrofit.Builder()
        .baseUrl(config.baseUrl)
        .callFactory(Call.Factory { request -> okHttpClient.get().newCall(request) })
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(WeatherApi::class.java)
}
