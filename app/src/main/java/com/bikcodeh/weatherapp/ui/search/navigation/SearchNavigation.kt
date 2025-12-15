package com.bikcodeh.weatherapp.ui.search.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bikcodeh.weatherapp.ui.navigation.Destination
import com.bikcodeh.weatherapp.ui.search.SearchScreen
import com.bikcodeh.weatherapp.ui.search.viewmodel.SearchViewModel

fun NavGraphBuilder.searchRoute(
    onNavigate: (query: String) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    composable(route = Destination.Search.route) {
        val searchViewModel = hiltViewModel<SearchViewModel>()
        SearchScreen(
            state = searchViewModel.state.collectAsState().value,
            effect = searchViewModel.effects,
            onEvent = searchViewModel::sendEvent,
            onNavigate = onNavigate,
            snackbarHostState = snackbarHostState
        )
    }
}