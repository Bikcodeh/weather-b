package com.bikcodeh.weatherapp.feature.search.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bikcodeh.weatherapp.feature.search.SearchScreen
import kotlinx.serialization.Serializable

/** Type-safe destination of the search screen. */
@Serializable
data object SearchRoute

/**
 * Adds the search screen to the graph. The feature does not know where a selected location
 * leads: the caller decides through [onLocationClick].
 */
fun NavGraphBuilder.searchScreen(onLocationClick: (locationId: Long) -> Unit) {
    composable<SearchRoute> { backStackEntry ->
        SearchScreen(
            onLocationClick = { locationId ->
                // Ignores clicks while the screen is leaving (e.g. a double tap), which would
                // otherwise push the destination twice.
                if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    onLocationClick(locationId)
                }
            },
        )
    }
}
