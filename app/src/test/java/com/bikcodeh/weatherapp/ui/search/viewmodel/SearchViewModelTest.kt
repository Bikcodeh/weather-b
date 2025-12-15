package com.bikcodeh.weatherapp.ui.search.viewmodel

import app.cash.turbine.test
import com.bikcodeh.weatherapp.CoroutineRule
import com.bikcodeh.weatherapp.TestDispatcherProvider
import com.bikcodeh.weatherapp.data.remote.dto.LocationDto
import com.bikcodeh.weatherapp.domain.commons.DispatcherProvider
import com.bikcodeh.weatherapp.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
    val coroutineRule = CoroutineRule()

    private lateinit var repository: WeatherRepository
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {

        repository = mockk()
        dispatcherProvider = TestDispatcherProvider(UnconfinedTestDispatcher())

        viewModel = SearchViewModel(
            weatherRepository = repository,
            dispatcher = dispatcherProvider
        )
    }

    @Test
    fun `initial state is correct`() {
        val state = viewModel.state.value

        assertThat(state.query).isEmpty()
        assertThat(state.locations).isEmpty()
        assertThat(state.isLoading).isFalse()
    }

    @Test
    fun `blank query clears locations and stops loading`() = runTest {
        viewModel.sendEvent(SearchEvent.OnQueryChanged(""))

        advanceUntilIdle()

        val state = viewModel.state.value

        assertThat(state.locations).isEmpty()
        assertThat(state.isLoading).isFalse()
    }


    @Test
    fun `search success updates locations`() = runTest {
        val dto = LocationDto(
            id = 1,
            name = "Bogotá",
            country = "Colombia",
            region = "Cundinamarca"
        )

        coEvery { repository.getSearch("Bog") } returns Result.success(
            listOf(dto, dto.copy(id = 2))
        )

        viewModel.sendEvent(SearchEvent.OnQueryChanged("Bog"))

        // delay(400) + corrutinas
        advanceTimeBy(400)
        advanceUntilIdle()

        val state = viewModel.state.value

        assertThat(state.isLoading).isFalse()
        assertThat(state.locations).hasSize(2)
        assertThat(state.locations.first().name).isEqualTo("Bogotá")
    }

    @Test
    fun `search failure emits ShowErrorMessage effect`() = runTest {
        val error = RuntimeException("Network error")

        coEvery { repository.getSearch("Bog") } returns Result.failure(error)

        viewModel.effects.test {
            viewModel.sendEvent(SearchEvent.OnQueryChanged("Bog"))

            advanceTimeBy(400)
            advanceUntilIdle()

            val effect = awaitItem()

            assertThat(effect).isInstanceOf(SearchEffect.ShowErrorMessage::class.java)
        }
    }

    @Test
    fun `on location selected emits Navigate effect`() = runTest {
        viewModel.effects.test {
            viewModel.sendEvent(SearchEvent.OnLocationSelected("Bogota"))

            val effect = awaitItem()

            assertThat(effect).isEqualTo(
                SearchEffect.Navigate("Bogota")
            )
        }
    }
}
