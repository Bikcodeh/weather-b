package com.bikcodeh.weatherapp.ui.navigation

import android.net.Uri

sealed class Destination(val route: String) {
    object Search : Destination("search")
    object Detail : Destination("detail") {
        const val QUERY_ARG = "query"

        val routeWithArgs = "detail?$QUERY_ARG={$QUERY_ARG}"

        fun createRoute(query: String): String =
            "detail?$QUERY_ARG=${Uri.encode(query)}"
    }
}