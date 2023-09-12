package com.example.musicapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.fragment
import androidx.navigation.navigation
import com.example.musicapp.presentation.home.HomeFragment

fun NavGraphBuilder.appNavigationGraph() {
    navigation(
        startDestination = AppNavigationGraphRoute.HomeFragment.buildFullPath(),
        route = AppNavigationGraphRoute.AppGraph.buildFullPath()
    ){
        addDestination()
    }
}

fun NavGraphBuilder.addDestination(){
    fragment<HomeFragment>(
        route = AppNavigationGraphRoute.HomeFragment.buildFullPath()
    ){
        label = "HomeFragment"
    }

}