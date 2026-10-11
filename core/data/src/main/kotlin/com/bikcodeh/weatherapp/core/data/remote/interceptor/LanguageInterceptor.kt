package com.bikcodeh.weatherapp.core.data.remote.interceptor

import java.util.Locale
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Asks the API for texts in the user's language (e.g. "Parcialmente nublado") by appending it as
 * the [LANGUAGE_QUERY_PARAMETER]. English is the API default, so it is not sent.
 *
 * @param currentLanguage Read on every request, so in-app language changes apply right away.
 */
internal class LanguageInterceptor(
    private val currentLanguage: () -> String = { Locale.getDefault().language },
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val language = currentLanguage()
        if (language.isBlank() || language == DEFAULT_LANGUAGE) return chain.proceed(request)

        val localizedUrl = request.url.newBuilder()
            .addQueryParameter(LANGUAGE_QUERY_PARAMETER, language)
            .build()
        return chain.proceed(request.newBuilder().url(localizedUrl).build())
    }

    companion object {
        const val LANGUAGE_QUERY_PARAMETER = "lang"
        private const val DEFAULT_LANGUAGE = "en"
    }
}
