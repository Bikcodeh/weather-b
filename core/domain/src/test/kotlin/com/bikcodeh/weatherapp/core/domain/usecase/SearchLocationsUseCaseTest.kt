package com.bikcodeh.weatherapp.core.domain.usecase

import com.bikcodeh.weatherapp.core.domain.error.DomainError
import com.bikcodeh.weatherapp.core.domain.result.DomainResult
import com.bikcodeh.weatherapp.core.testing.data.TestData
import com.bikcodeh.weatherapp.core.testing.repository.FakeWeatherRepository
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SearchLocationsUseCaseTest {

    private val repository = FakeWeatherRepository()
    private val searchLocations = SearchLocationsUseCase(repository)

    @Test
    fun `trims the query before searching`() = runTest {
        searchLocations("  Bogota  ")

        assertThat(repository.searchQueries).containsExactly("Bogota")
    }

    @Test
    fun `queries shorter than the minimum return an empty list without searching`() = runTest {
        val result = searchLocations(" B ")

        assertThat(result).isEqualTo(DomainResult.Success(emptyList<Nothing>()))
        assertThat(repository.searchQueries).isEmpty()
    }

    @Test
    fun `returns the locations found by the repository`() = runTest {
        repository.searchResult = DomainResult.Success(listOf(TestData.bogota))

        val result = searchLocations("Bog")

        assertThat(result).isEqualTo(DomainResult.Success(listOf(TestData.bogota)))
    }

    @Test
    fun `propagates repository failures`() = runTest {
        repository.searchResult = DomainResult.Failure(DomainError.Network)

        val result = searchLocations("Bog")

        assertThat(result).isEqualTo(DomainResult.Failure(DomainError.Network))
    }
}
