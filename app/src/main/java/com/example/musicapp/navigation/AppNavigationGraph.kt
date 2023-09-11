package com.example.musicapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.fragment.fragment
import androidx.navigation.navigation
import com.example.musicapp.data.model.Song
import com.example.musicapp.presentation.home.HomeFragment
import com.example.musicapp.presentation.player.PlayerFragment

fun NavGraphBuilder.appNavigationGraph() {
    navigation(
        startDestination = AppNavigationGraphRoute.PlayerFragment.buildFullPath(),
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

    fragment<PlayerFragment>(
        route = AppNavigationGraphRoute.PlayerFragment.buildFullPath()
    ){
        label = "PlayerFragment"
        /*argument(AppNavigationGraphArguments.SELECTED_SONG) {
            type = NavType.ParcelableType(
                type = Song::class.java
            )
        }
        argument(AppNavigationGraphArguments.SONG_LIST) {
            type = NavType.ParcelableArrayType(
                type = Song::class.java
            )
        }*/
    }
}