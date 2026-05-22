package com.bikcodeh.weatherapp.domain.usecase

import com.bikcodeh.weatherapp.domain.model.Location
import com.bikcodeh.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class SearchLocationUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(query: String): Result<List<Location>> {
        return weatherRepository.getSearch(query)
    }
}
