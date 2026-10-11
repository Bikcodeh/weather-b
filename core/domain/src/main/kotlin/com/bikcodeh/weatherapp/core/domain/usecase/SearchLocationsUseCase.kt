package com.bikcodeh.weatherapp.core.domain.usecase

import com.bikcodeh.weatherapp.core.domain.model.Location
import com.bikcodeh.weatherapp.core.domain.repository.WeatherRepository
import com.bikcodeh.weatherapp.core.domain.result.DomainResult
import javax.inject.Inject

/**
 * Searches locations by name.
 *
 * The query is trimmed, and queries shorter than [MIN_QUERY_LENGTH] resolve to an empty list
 * without reaching the repository: they produce meaningless results and waste API quota.
 */
class SearchLocationsUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {

    suspend operator fun invoke(query: String): DomainResult<List<Location>> {
        val normalizedQuery = query.trim()
        if (normalizedQuery.length < MIN_QUERY_LENGTH) return DomainResult.Success(emptyList())
        return weatherRepository.searchLocations(normalizedQuery)
    }

    companion object {
        /** Minimum number of characters (after trimming) required to run a search. */
        const val MIN_QUERY_LENGTH = 2
    }
}
