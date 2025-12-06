package com.example.weatherapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.view.screen.FavouriteLocationScreen
import com.example.weatherapp.view.screen.SearchWeatherScreen
import com.example.weatherapp.view.utils.StringConstants
import com.example.weatherapp.viewmodel.SearchWeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchWeatherView : ComponentActivity() {

    private val viewModel: SearchWeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController();

            NavHost(
                navController = navController,
                startDestination = StringConstants.SEARCH_WEATHER_SCREEN
            ) {
                composable(StringConstants.SEARCH_WEATHER_SCREEN) {
                    SearchWeatherScreen(viewModel, navController)
                }

                composable(StringConstants.LOCATIONS_CATALOG_SCREEN) {
                    FavouriteLocationScreen(navController)
                }
            }
        }
    }
}
