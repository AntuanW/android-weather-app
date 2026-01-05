package com.example.weatherapp.view.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.weatherapp.view.composables.FavouriteLocationRow
import com.example.weatherapp.view.utils.StringConstants
import com.example.weatherapp.viewmodel.SearchWeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteLocationScreen(
    viewModel: SearchWeatherViewModel,
    navController: NavController
) {
    val favourites by viewModel.favourites.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(StringConstants.LOCATIONS_CATALOG_TITLE) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        if (favourites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No favourite locations yet ⭐")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(favourites) { location ->
                    FavouriteLocationRow(
                        location = location,
                        onClick = {
                            viewModel.searchFromFavourite(location)
                            navController.popBackStack()
                        },
                        onRemove = {
                            viewModel.removeFavourite(location)
                        }
                    )
                }
            }
        }
    }
}