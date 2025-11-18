package com.example.weatherapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                        label = { Text("Location") }
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
                            Column(
                                modifier = Modifier.padding(top = 15.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text("Temperature category: ${data.temperature}")
                                Text("Air Condition: ${data.airCondition}")
                                Text("Forecast: ${data.forecast}")
                                Text("Temperature: ${data.tempC}°C")
                            }
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
