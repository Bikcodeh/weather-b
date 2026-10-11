package com.bikcodeh.weatherapp.feature.detail.navigation

import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bikcodeh.weatherapp.feature.detail.DetailScreen
import kotlinx.serialization.Serializable

/**
 * Type-safe destination of the detail screen.
 *
 * @property locationId Id of the location whose weather is shown.
 */
@Serializable
data class DetailRoute(
    val locationId: Long,
)

/** Navigates to the weather detail of [locationId]. */
fun NavController.navigateToDetail(
    locationId: Long,
    navOptions: NavOptionsBuilder.() -> Unit = {},
) {
    navigate(route = DetailRoute(locationId), builder = navOptions)
}

/** Adds the detail screen to the graph. */
fun NavGraphBuilder.detailScreen(onBackClick: () -> Unit) {
    composable<DetailRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<DetailRoute>()
        DetailScreen(
            locationId = route.locationId,
            // Ignores repeated clicks while leaving the screen, which would pop the previous one.
            onBackClick = dropUnlessResumed(block = onBackClick),
        )
    }
}
