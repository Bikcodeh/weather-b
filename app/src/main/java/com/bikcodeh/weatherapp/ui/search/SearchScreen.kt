package com.bikcodeh.weatherapp.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.bikcodeh.weatherapp.ui.search.viewmodel.SearchEffect
import com.bikcodeh.weatherapp.ui.search.viewmodel.SearchEvent
import com.bikcodeh.weatherapp.ui.search.viewmodel.SearchState
import com.bikcodeh.weatherapp.ui.theme.WeatherBlueBottom
import com.bikcodeh.weatherapp.ui.theme.WeatherBlueMid
import com.bikcodeh.weatherapp.ui.theme.WeatherBlueTop
import kotlinx.coroutines.flow.Flow

@Composable
fun SearchScreen(
    state: SearchState,
    effect: Flow<SearchEffect>,
    onEvent: (SearchEvent) -> Unit,
    onNavigate: (query: String) -> Unit,
    snackbarHostState: SnackbarHostState
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    LaunchedEffect(effect) {
        effect.collect { effect ->
            when (effect) {
                is SearchEffect.Navigate -> {
                    onNavigate(effect.query)
                }

                is SearchEffect.ShowErrorMessage -> {
                    keyboardController?.hide()
                    snackbarHostState.showSnackbar(
                        message = context.getString(effect.errorMessage),
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        WeatherBlueTop,
                        WeatherBlueMid,
                        WeatherBlueBottom
                    )
                )
            )
    ) {
        SearchContent(
            state = state,
            onQueryChange = { onEvent(SearchEvent.OnQueryChanged(it)) },
            onItemClick = onNavigate
        )
    }
}