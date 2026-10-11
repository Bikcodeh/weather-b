package com.bikcodeh.weatherapp.core.data.remote

import com.bikcodeh.weatherapp.core.data.remote.dto.ForecastResponseDto
import com.bikcodeh.weatherapp.core.data.remote.dto.LocationDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit definition of the WeatherAPI.com endpoints used by the app.
 * Non-2xx responses are thrown as [retrofit2.HttpException].
 */
internal interface WeatherApi {

    /** Autocomplete search of locations whose name matches [query]. */
    @GET("search.json")
    suspend fun searchLocations(@Query("q") query: String): List<LocationDto>

    /**
     * Current weather plus [days] days of forecast.
     *
     * @param query Location selector. `id:<id>` targets a location returned by [searchLocations].
     */
    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") query: String,
        @Query("days") days: Int,
    ): ForecastResponseDto
}
