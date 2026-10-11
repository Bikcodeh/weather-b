package com.bikcodeh.weatherapp.core.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/** Authenticates every request by appending the API key as the [API_KEY_QUERY_PARAMETER]. */
internal class ApiKeyInterceptor(
    private val apiKey: String,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedUrl = request.url.newBuilder()
            .addQueryParameter(API_KEY_QUERY_PARAMETER, apiKey)
            .build()
        return chain.proceed(request.newBuilder().url(authenticatedUrl).build())
    }

    companion object {
        const val API_KEY_QUERY_PARAMETER = "key"
    }
}
