package com.bikcodeh.weatherapp.core.data

/**
 * Configuration of the WeatherAPI.com client.
 *
 * It is provided by the application module (the composition root), which owns secrets and build
 * flags, so this library does not need its own `BuildConfig`.
 *
 * @property apiKey Key sent with every request. Never logged.
 * @property enableHttpLogging Logs requests and responses; meant for debug builds only.
 * @property baseUrl Root URL of the API, ending with `/`.
 */
class WeatherApiConfig(
    val apiKey: String,
    val enableHttpLogging: Boolean,
    val baseUrl: String = DEFAULT_BASE_URL,
) {

    override fun toString(): String =
        "WeatherApiConfig(baseUrl=$baseUrl, enableHttpLogging=$enableHttpLogging, apiKey=██)"

    companion object {
        const val DEFAULT_BASE_URL = "https://api.weatherapi.com/v1/"
    }
}
