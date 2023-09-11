package com.example.musicapp.navigation

sealed class AppNavigationGraphRoute(
    var route: String,
    private var argKeys: List<String> = listOf()
) : NavigationDestinationBuilder by NavigationDestinationBuilderImpl(route, argKeys.map { "{$it}" }) {

    object AppGraph : AppNavigationGraphRoute(
        route = "Graph"
    )

    object HomeFragment : AppNavigationGraphRoute(
        route = "Home"
    )

    object PlayerFragment : AppNavigationGraphRoute(
        route = "Player"
    )
}