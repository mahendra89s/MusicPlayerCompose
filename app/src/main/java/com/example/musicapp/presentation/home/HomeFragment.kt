package com.example.musicapp.presentation.home

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.musicapp.presentation.base.BaseComposeFragment
import com.example.musicapp.presentation.home.model.HomeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseComposeFragment<HomeEvent>() {
    private val viewModel: HomeViewModel by viewModels()

    @Composable
    override fun Screen() {
        HomeScreen(viewModel = viewModel, navController = findNavController())
        viewModel.handleEvent(HomeEvent.OnScreenLoad)
    }
}