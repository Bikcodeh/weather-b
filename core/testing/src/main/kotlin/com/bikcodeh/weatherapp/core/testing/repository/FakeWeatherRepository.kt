package com.bikcodeh.weatherapp.core.testing.repository

import com.bikcodeh.weatherapp.core.domain.model.Location
import com.bikcodeh.weatherapp.core.domain.model.WeatherForecast
import com.bikcodeh.weatherapp.core.domain.repository.WeatherRepository
import com.bikcodeh.weatherapp.core.domain.result.DomainResult
import com.bikcodeh.weatherapp.core.testing.data.TestData
import kotlinx.coroutines.delay

/**
 * In-memory [WeatherRepository] for tests.
 *
 * Configure the responses through [searchResult] and [forecastResult]; the received arguments are
 * recorded in [searchQueries] and [forecastRequests]. [responseDelayMillis] simulates latency
 * (in virtual time when used inside `runTest`), which makes loading states observable.
 */
class FakeWeatherRepository : WeatherRepository {

    var searchResult: DomainResult<List<Location>> = DomainResult.Success(emptyList())
    var forecastResult: DomainResult<WeatherForecast> =
        DomainResult.Success(TestData.bogotaForecast)
    var responseDelayMillis: Long = 0

    private val recordedSearchQueries = mutableListOf<String>()
    val searchQueries: List<String> get() = recordedSearchQueries

    private val recordedForecastRequests = mutableListOf<ForecastRequest>()
    val forecastRequests: List<ForecastRequest> get() = recordedForecastRequests

    override suspend fun searchLocations(query: String): DomainResult<List<Location>> {
        recordedSearchQueries += query
        delay(responseDelayMillis)
        return searchResult
    }

    override suspend fun getForecast(locationId: Long, days: Int): DomainResult<WeatherForecast> {
        recordedForecastRequests += ForecastRequest(locationId, days)
        delay(responseDelayMillis)
        return forecastResult
    }

    /** Arguments received by [getForecast]. */
    data class ForecastRequest(
        val locationId: Long,
        val days: Int,
    )
}
