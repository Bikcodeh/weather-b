package com.bikcodeh.weatherapp.feature.search

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
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
import com.bikcodeh.weatherapp.core.designsystem.component.LoopingLottieAnimation
import com.bikcodeh.weatherapp.core.designsystem.component.WeatherTopAppBar
import com.bikcodeh.weatherapp.core.designsystem.theme.WeatherTheme
import com.bikcodeh.weatherapp.feature.search.component.LocationCard
import com.bikcodeh.weatherapp.feature.search.component.SearchField
import com.bikcodeh.weatherapp.feature.search.model.LocationUiModel

/**
 * Stateful entry point of the search feature: connects [SearchViewModel] with [SearchContent].
 *
 * The text field state lives here (it is UI element state and must be updated synchronously while
 * typing); the ViewModel only receives the resulting query through [SearchViewModel.onQueryChange].
 */
@Composable
internal fun SearchScreen(
    onLocationClick: (locationId: Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val queryState = rememberTextFieldState()

    LaunchedEffect(queryState, viewModel) {
        snapshotFlow { queryState.text.toString() }.collect(viewModel::onQueryChange)
    }

    SearchContent(
        queryState = queryState,
        uiState = uiState,
        onLocationClick = onLocationClick,
        onRetryClick = viewModel::onRetry,
        modifier = modifier,
    )
}

/** Stateless search screen, driven only by its parameters (previews and UI tests use it). */
@Composable
internal fun SearchContent(
    queryState: TextFieldState,
    uiState: SearchUiState,
    onLocationClick: (locationId: Long) -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GradientBackground(modifier = modifier) {
        Scaffold(
            topBar = { WeatherTopAppBar(title = stringResource(R.string.feature_search_title)) },
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
                    .imePadding(),
            ) {
                SearchField(
                    state = queryState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                )
                SearchResults(
                    uiState = uiState,
                    onLocationClick = onLocationClick,
                    onRetryClick = onRetryClick,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
private fun SearchResults(
    uiState: SearchUiState,
    onLocationClick: (locationId: Long) -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        SearchUiState.Idle -> Illustration(
            animationRes = R.raw.feature_search_plane,
            modifier = modifier,
        )

        SearchUiState.Loading -> LoadingIndicator(modifier = modifier)

        SearchUiState.Empty -> Illustration(
            animationRes = R.raw.feature_search_not_found,
            modifier = modifier,
            message = stringResource(R.string.feature_search_no_results),
        )

        is SearchUiState.Success -> LocationList(
            locations = uiState.locations,
            onLocationClick = onLocationClick,
            modifier = modifier,
        )

        is SearchUiState.Error -> ErrorState(
            message = stringResource(uiState.messageRes),
            onRetryClick = onRetryClick,
            modifier = modifier,
        )
    }
}

@Composable
private fun LocationList(
    locations: List<LocationUiModel>,
    onLocationClick: (locationId: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(items = locations, key = LocationUiModel::id) { location ->
            LocationCard(
                location = location,
                onClick = { onLocationClick(location.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
            )
        }
    }
}

@Composable
private fun Illustration(
    @RawRes animationRes: Int,
    modifier: Modifier = Modifier,
    message: String? = null,
) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
    ) {
        if (message != null) {
            Text(text = message, style = MaterialTheme.typography.headlineSmall)
        }
        LoopingLottieAnimation(animationRes = animationRes, modifier = Modifier.size(220.dp))
    }
}

@Preview
@Composable
private fun SearchContentResultsPreview() {
    WeatherTheme {
        SearchContent(
            queryState = TextFieldState("Bog"),
            uiState = SearchUiState.Success(
                listOf(
                    LocationUiModel(id = 1, name = "Bogota", subtitle = "Cundinamarca, Colombia"),
                    LocationUiModel(
                        id = 2,
                        name = "Bogota",
                        subtitle = "New Jersey, United States of America",
                    ),
                ),
            ),
            onLocationClick = {},
            onRetryClick = {},
        )
    }
}

@Preview
@Composable
private fun SearchContentIdlePreview() {
    WeatherTheme {
        SearchContent(
            queryState = TextFieldState(),
            uiState = SearchUiState.Idle,
            onLocationClick = {},
            onRetryClick = {},
        )
    }
}
