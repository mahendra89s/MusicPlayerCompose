package com.example.musicapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun HandleNavigation(
    navigationFlow: SharedFlow<NavigationState>,
    navigate: (NavigationState) -> Unit
) {
    val navigation = navigationFlow.collectAsState(initial = NavigationState.EmptyNavigation)
    LaunchedEffect(key1 = navigation.value) {
        navigate(navigation.value)
    }
}