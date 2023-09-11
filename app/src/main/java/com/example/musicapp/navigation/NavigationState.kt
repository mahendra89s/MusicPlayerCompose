package com.example.musicapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.navOptions

sealed class NavigationState : NavigationHandler {
    object NavigateUp : NavigationState() {

        override fun navigate(navController: NavController) {
            navController.navigateUp()
        }
    }

    class NavigateToDestination(
        private val navigationGraphRoute: AppNavigationGraphRoute,
        private val clearBackStack: Boolean = false,
        private vararg val argsValues: List<String>
    ) : NavigationState(),
        NavigationDestinationBuilder by NavigationDestinationBuilderImpl(
            route = navigationGraphRoute.route,
            argsValues = argsValues
        )
    {
        override fun navigate(navController: NavController) {
            val navOptions = if (!clearBackStack) navOptions {} else navOptions {
                launchSingleTop = true
                restoreState = false
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                    inclusive = true
                }
            }
            navController.navigate(buildFullPath(), navOptions)
        }
    }

    object EmptyNavigation : NavigationState() {
        override fun navigate(navController: NavController) {
        }
    }
}