package com.bikcodeh.weatherapp.ui.detail

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
import com.bikcodeh.weatherapp.ui.detail.viewmodel.DetailEffect
import com.bikcodeh.weatherapp.ui.detail.viewmodel.DetailState
import com.bikcodeh.weatherapp.ui.theme.WeatherBlueBottom
import com.bikcodeh.weatherapp.ui.theme.WeatherBlueMid
import com.bikcodeh.weatherapp.ui.theme.WeatherBlueTop
import kotlinx.coroutines.flow.Flow

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    state: DetailState,
    effect: Flow<DetailEffect>,
    snackbarHostState: SnackbarHostState,
    onInit: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(true) {
        onInit()
    }

    LaunchedEffect(effect) {
        effect.collect { effect ->
            when (effect) {
                is DetailEffect.ShowErrorMessage -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(effect.errorMessage),
                        duration = SnackbarDuration.Short
                    )

                }
            }
        }
    }

    Box(
        modifier = modifier
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
        DetailContent(state)
    }
}