package com.bikcodeh.weatherapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getString
import androidx.navigation.NavHostController
import com.bikcodeh.weatherapp.R
import com.bikcodeh.weatherapp.ui.components.AppTopBar
import com.bikcodeh.weatherapp.ui.components.SetStatusBar
import com.bikcodeh.weatherapp.ui.components.TopBarState
import com.bikcodeh.weatherapp.ui.navigation.Destination
import com.bikcodeh.weatherapp.ui.navigation.SetupNavGraph
import com.bikcodeh.weatherapp.ui.theme.WeatherBlueTop

@Composable
fun MainScaffold(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    currentRoute: String?
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val topBarState = when {
        currentRoute == Destination.Search.route ->
            TopBarState(
                title = getString(context, R.string.search_screen_title),
                showBack = false
            )

        currentRoute?.startsWith("detail") == true ->
            TopBarState(
                title = getString(context, R.string.detail_screen_title),
                showBack = true
            )

        else -> null
    }

    SetStatusBar(
        color = WeatherBlueTop,
        darkIcons = false
    )
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            topBarState?.let {
                AppTopBar(
                    title = it.title,
                    showBack = it.showBack,
                    onBackClick = { navHostController.popBackStack() }
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            }
        }
    ) { paddingValues ->
        SetupNavGraph(navHostController, paddingValues, snackbarHostState)
    }
}