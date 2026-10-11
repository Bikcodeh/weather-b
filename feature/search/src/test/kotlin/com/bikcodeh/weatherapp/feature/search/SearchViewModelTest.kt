package com.bikcodeh.weatherapp.feature.search

import app.cash.turbine.test
import com.bikcodeh.weatherapp.core.domain.error.DomainError
import com.bikcodeh.weatherapp.core.domain.result.DomainResult
import com.bikcodeh.weatherapp.core.domain.usecase.SearchLocationsUseCase
import com.bikcodeh.weatherapp.core.testing.MainDispatcherRule
import com.bikcodeh.weatherapp.core.testing.data.TestData
import com.bikcodeh.weatherapp.core.testing.repository.FakeWeatherRepository
import com.bikcodeh.weatherapp.core.ui.error.messageRes
import com.bikcodeh.weatherapp.feature.search.model.LocationUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // A small latency makes the Loading state observable before the result arrives.
    private val repository = FakeWeatherRepository().apply { responseDelayMillis = 100 }
    private lateinit var viewModel: SearchViewModel

    // Created after MainDispatcherRule installs the test dispatcher: viewModelScope reads
    // Dispatchers.Main when the ViewModel is constructed.
    @Before
    fun setUp() {
        viewModel = SearchViewModel(SearchLocationsUseCase(repository))
    }

    @Test
    fun `initial state is idle`() {
        assertThat(viewModel.uiState.value).isEqualTo(SearchUiState.Idle)
    }

    @Test
    fun `a valid query emits loading and then the results`() = runTest {
        repository.searchResult = DomainResult.Success(listOf(TestData.bogota))

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(SearchUiState.Idle)

            viewModel.onQueryChange("Bogota")

            assertThat(awaitItem()).isEqualTo(SearchUiState.Loading)
            assertThat(awaitItem()).isEqualTo(
                SearchUiState.Success(
                    listOf(
                        LocationUiModel(
                            id = TestData.bogota.id,
                            name = "Bogota",
                            subtitle = "Cundinamarca, Colombia",
                        ),
                    ),
                ),
            )
        }
    }

    @Test
    fun `short queries stay idle without searching`() = runTest {
        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(SearchUiState.Idle)

            viewModel.onQueryChange("B")
            advanceUntilIdle()

            expectNoEvents()
        }
        assertThat(repository.searchQueries).isEmpty()
    }

    @Test
    fun `typing quickly searches only the last query`() = runTest {
        viewModel.uiState.test {
            skipItems(1)

            viewModel.onQueryChange("Bo")
            advanceTimeBy(100)
            viewModel.onQueryChange("Bog")
            advanceTimeBy(100)
            viewModel.onQueryChange("Bogo")
            advanceUntilIdle()

            cancelAndIgnoreRemainingEvents()
        }
        assertThat(repository.searchQueries).containsExactly("Bogo")
    }

    @Test
    fun `the query is trimmed before searching`() = runTest {
        viewModel.uiState.test {
            skipItems(1)

            viewModel.onQueryChange("  Bogota  ")
            advanceUntilIdle()

            cancelAndIgnoreRemainingEvents()
        }
        assertThat(repository.searchQueries).containsExactly("Bogota")
    }

    @Test
    fun `no matches emit the empty state`() = runTest {
        repository.searchResult = DomainResult.Success(emptyList())

        viewModel.uiState.test {
            skipItems(1)

            viewModel.onQueryChange("Xyzzy")

            assertThat(awaitItem()).isEqualTo(SearchUiState.Loading)
            assertThat(awaitItem()).isEqualTo(SearchUiState.Empty)
        }
    }

    @Test
    fun `failures emit an error with the matching message`() = runTest {
        repository.searchResult = DomainResult.Failure(DomainError.Network)

        viewModel.uiState.test {
            skipItems(1)

            viewModel.onQueryChange("Bogota")

            assertThat(awaitItem()).isEqualTo(SearchUiState.Loading)
            assertThat(awaitItem()).isEqualTo(SearchUiState.Error(DomainError.Network.messageRes()))
        }
    }

    @Test
    fun `retry runs the last search again`() = runTest {
        repository.searchResult = DomainResult.Failure(DomainError.Timeout)

        viewModel.uiState.test {
            skipItems(1)
            viewModel.onQueryChange("Bogota")
            skipItems(2) // Loading, Error

            repository.searchResult = DomainResult.Success(listOf(TestData.bogota))
            viewModel.onRetry()

            assertThat(awaitItem()).isEqualTo(SearchUiState.Loading)
            assertThat(awaitItem()).isInstanceOf(SearchUiState.Success::class.java)
        }
        assertThat(repository.searchQueries).containsExactly("Bogota", "Bogota")
    }

    @Test
    fun `clearing the query goes back to idle`() = runTest {
        repository.searchResult = DomainResult.Success(listOf(TestData.bogota))

        viewModel.uiState.test {
            skipItems(1)
            viewModel.onQueryChange("Bogota")
            skipItems(2) // Loading, Success

            viewModel.onQueryChange("")

            assertThat(awaitItem()).isEqualTo(SearchUiState.Idle)
        }
    }
}
