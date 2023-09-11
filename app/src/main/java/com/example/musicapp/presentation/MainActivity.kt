package com.example.musicapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.musicapp.databinding.ActivityMainBinding
import com.example.musicapp.navigation.AppNavigationGraphRoute
import com.example.musicapp.navigation.mainNavigationController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigationGraph(AppNavigationGraphRoute.AppGraph.buildFullPath())
    }
    private fun setUpNavigationGraph(
        initialNavigationDestination: String
    ) {
        navController = getNavigationController()
        navController.graph = mainNavigationController(
            navController = navController,
            graphStartDestination = initialNavigationDestination
        )
    }

    private fun getNavigationController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostContainer.id) as NavHostFragment
        return navHostFragment.navController
    }
}
