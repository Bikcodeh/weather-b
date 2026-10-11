package com.bikcodeh.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bikcodeh.weatherapp.feature.detail.navigation.detailScreen
import com.bikcodeh.weatherapp.feature.detail.navigation.navigateToDetail
import com.bikcodeh.weatherapp.feature.search.navigation.SearchRoute
import com.bikcodeh.weatherapp.feature.search.navigation.searchScreen

/**
 * Navigation graph of the app. Features do not depend on each other: this is the only place that
 * knows that selecting a search result opens its detail.
 */
@Composable
fun WeatherNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = SearchRoute,
        modifier = modifier,
    ) {
        searchScreen(onLocationClick = { locationId -> navController.navigateToDetail(locationId) })
        detailScreen(onBackClick = { navController.popBackStack() })
    }
}
