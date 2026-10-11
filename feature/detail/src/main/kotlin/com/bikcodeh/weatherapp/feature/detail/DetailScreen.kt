package com.bikcodeh.weatherapp.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bikcodeh.weatherapp.core.designsystem.component.ErrorState
import com.bikcodeh.weatherapp.core.designsystem.component.GradientBackground
import com.bikcodeh.weatherapp.core.designsystem.component.LoadingIndicator
import com.bikcodeh.weatherapp.core.designsystem.component.WeatherTopAppBar
import com.bikcodeh.weatherapp.core.designsystem.theme.WeatherTheme
import com.bikcodeh.weatherapp.feature.detail.component.CurrentWeatherHeader
import com.bikcodeh.weatherapp.feature.detail.component.ForecastDaysRow
import com.bikcodeh.weatherapp.feature.detail.component.WeatherStatsRow
import com.bikcodeh.weatherapp.feature.detail.model.ForecastDayUiModel
import com.bikcodeh.weatherapp.feature.detail.model.WeatherDetailUiModel
import java.time.LocalDate

/** Stateful entry point of the detail feature: connects [DetailViewModel] with [DetailContent]. */
@Composable
internal fun DetailScreen(
    locationId: Long,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel<DetailViewModel, DetailViewModel.Factory>(
        creationCallback = { factory -> factory.create(locationId) },
    ),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetailContent(
        uiState = uiState,
        onBackClick = onBackClick,
        onRetryClick = viewModel::onRetry,
        modifier = modifier,
    )
}

/** Stateless detail screen, driven only by its parameters (previews and UI tests use it). */
@Composable
internal fun DetailContent(
    uiState: DetailUiState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GradientBackground(modifier = modifier) {
        Scaffold(
            topBar = {
                WeatherTopAppBar(
                    title = stringResource(R.string.feature_detail_title),
                    onBackClick = onBackClick,
                )
            },
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ) { innerPadding ->
            val contentModifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)

            when (uiState) {
                DetailUiState.Loading -> LoadingIndicator(modifier = contentModifier)

                is DetailUiState.Error -> ErrorState(
                    message = stringResource(uiState.messageRes),
                    onRetryClick = onRetryClick,
                    modifier = contentModifier,
                )

                is DetailUiState.Success -> WeatherDetail(
                    weather = uiState.weather,
                    modifier = contentModifier,
                )
            }
        }
    }
}

@Composable
private fun WeatherDetail(weather: WeatherDetailUiModel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        CurrentWeatherHeader(weather = weather)
        WeatherStatsRow(
            precipitationMm = weather.precipitationMm,
            humidityPercent = weather.humidityPercent,
            windSpeedKph = weather.windSpeedKph,
            modifier = Modifier.fillMaxWidth(),
        )
        if (weather.upcomingDays.isNotEmpty()) {
            ForecastDaysRow(days = weather.upcomingDays, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview
@Composable
private fun DetailContentPreview() {
    WeatherTheme {
        DetailContent(
            uiState = DetailUiState.Success(
                WeatherDetailUiModel(
                    locationName = "Yumbo",
                    date = LocalDate.of(2025, 12, 16),
                    temperatureCelsius = 27,
                    feelsLikeCelsius = 29,
                    condition = "Sunny",
                    conditionIconUrl = "",
                    precipitationMm = 0.0,
                    humidityPercent = 40,
                    windSpeedKph = 3,
                    upcomingDays = listOf(
                        ForecastDayUiModel(LocalDate.of(2025, 12, 17), 30, 19, "Sunny", ""),
                        ForecastDayUiModel(LocalDate.of(2025, 12, 18), 28, 18, "Patchy rain", ""),
                    ),
                ),
            ),
            onBackClick = {},
            onRetryClick = {},
        )
    }
}

@Preview
@Composable
private fun DetailContentLoadingPreview() {
    WeatherTheme {
        DetailContent(uiState = DetailUiState.Loading, onBackClick = {}, onRetryClick = {})
    }
}
