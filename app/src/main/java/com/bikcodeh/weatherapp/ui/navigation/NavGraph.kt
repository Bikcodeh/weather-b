package com.bikcodeh.weatherapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bikcodeh.weatherapp.ui.detail.navigation.detailRoute
import com.bikcodeh.weatherapp.ui.search.navigation.searchRoute

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Search.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        searchRoute(
            onNavigate = { query ->
                navController.navigate(Destination.Detail.createRoute(query))
            },
            snackbarHostState = snackbarHostState
        )

        detailRoute(
            onBack = { navController.popBackStack() },
            snackbarHostState = snackbarHostState
        )
    }
}