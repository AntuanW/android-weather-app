package com.example.weatherapp.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.view.composables.WeatherCard
import com.example.weatherapp.viewmodel.SearchWeatherViewModel
import com.example.weatherapp.viewmodel.WeatherUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchWeatherView : ComponentActivity() {

    private val viewModel: SearchWeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsState()
            val location by viewModel.location.collectAsState()

            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    viewModel.fetchCurrentLocationNameAndWeather()
                }
            }

            LaunchedEffect(Unit) {
                permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }

            Scaffold { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TextField(
                        value = location,
                        onValueChange = { viewModel.onLocationChange(it) },
                        label = { Text("Location") },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    Log.i("SearchView", "Try to get permission")
                                    permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                                    Log.i("SearchView", "Asked to get permission")
                                }
                            ) {
                                Icon(Icons.Filled.MyLocation, contentDescription = "Use current location")
                            }
                        },
                        modifier = Modifier.fillMaxWidth(0.9f)
                    )

                    Button(
                        modifier = Modifier.padding(top = 20.dp),
                        onClick = {
                            viewModel.fetchWeatherSummary(location)
                        }
                    ) {
                        Text("Show Weather")
                    }

                    when (uiState) {
                        WeatherUiState.Idle -> {
                            Text(
                                modifier = Modifier.padding(top = 15.dp),
                                text = "Enter location to get current weather"
                            )
                        }

                        WeatherUiState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(top = 15.dp)
                            )
                        }

                        is WeatherUiState.Success -> {
                            val data = (uiState as WeatherUiState.Success).data

                            WeatherCard(
                                temp = data.tempC,
                                condition = data.airCondition,
                                forecast = data.forecast,
                                iconUrl = data.iconUrl,
                                modifier = Modifier.padding(top = 20.dp)
                            )
                        }

                        is WeatherUiState.Error -> {
                            val msg = (uiState as WeatherUiState.Error).message
                            Text(
                                modifier = Modifier.padding(top = 15.dp),
                                text = "Error: $msg"
                            )
                        }
                    }
                }
            }
        }
    }
}
