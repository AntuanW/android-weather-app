package com.example.weatherapp.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.model.database.model.Location
import com.example.weatherapp.view.composables.FavouriteLocationRow
import com.example.weatherapp.view.utils.StringConstants
import com.example.weatherapp.viewmodel.SearchWeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteLocationScreen(
    viewModel: SearchWeatherViewModel,
    navController: NavController
) {
    val favourites by viewModel.favouritesWithWeather.collectAsState()

    var showRemoveDialog by remember { mutableStateOf(false) }
    var locationToRemove by remember { mutableStateOf<Location?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        StringConstants.LOCATIONS_CATALOG_TITLE,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0288D1).copy(alpha = 0.85f)
                )
            )
        }

    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF81D4FA), Color(0xFF0288D1))
                    )
                )
                .padding(innerPadding)
        ) {

            if (favourites.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No favourite locations yet ⭐",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(favourites) { item ->
                        FavouriteLocationRow(
                            item = item,
                            onClick = {
                                viewModel.searchFromFavourite(item.location)
                                navController.popBackStack()
                            },
                            onRemove = {
                                locationToRemove = item.location
                                showRemoveDialog = true
                            }
                        )
                    }
                }
            }
        }
    }

    if (showRemoveDialog && locationToRemove != null) {
        AlertDialog(
            onDismissRequest = {
                showRemoveDialog = false
                locationToRemove = null
            },
            title = { Text("Remove from favourites?") },
            text = {
                Text(
                    "Do you really want to remove " +
                            locationToRemove!!.name +
                            " from your favourites?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.removeFavourite(locationToRemove!!)
                        showRemoveDialog = false
                        locationToRemove = null
                    }
                ) {
                    Text("Remove")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showRemoveDialog = false
                        locationToRemove = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
