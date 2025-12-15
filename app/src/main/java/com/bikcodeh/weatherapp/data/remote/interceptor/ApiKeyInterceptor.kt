package com.bikcodeh.weatherapp.data.remote.interceptor

import com.bikcodeh.weatherapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, BuildConfig.WEATHER_API_KEY)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }

    companion object {
        const val QUERY_API_KEY = "key"
    }
}