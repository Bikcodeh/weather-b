package com.bikcodeh.weatherapp.ui.detail.navigation

import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bikcodeh.weatherapp.ui.detail.DetailScreen
import com.bikcodeh.weatherapp.ui.detail.viewmodel.DetailEvent
import com.bikcodeh.weatherapp.ui.detail.viewmodel.DetailViewModel
import com.bikcodeh.weatherapp.ui.navigation.Destination

fun NavGraphBuilder.detailRoute(
    onBack: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    composable(
        route = Destination.Detail.routeWithArgs,
        arguments = listOf(
            navArgument(Destination.Detail.QUERY_ARG) {
                type = NavType.StringType
                nullable = true
            }
        )
    ) { backStackEntry ->

        val detailViewModel = hiltViewModel<DetailViewModel>()

        val query = backStackEntry.arguments
            ?.getString(Destination.Detail.QUERY_ARG)
            ?.let { Uri.decode(it) }

        DetailScreen(
            state = detailViewModel.state.collectAsState().value,
            effect = detailViewModel.effects,
            snackbarHostState = snackbarHostState,
            onInit = { detailViewModel.sendEvent(DetailEvent.LoadWeather(query ?: "")) },
        )
    }
}