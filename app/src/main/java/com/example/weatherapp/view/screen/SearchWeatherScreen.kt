package com.example.weatherapp.view.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.view.composables.HourlyForecastCard
import com.example.weatherapp.view.composables.MainWeatherCard
import com.example.weatherapp.view.utils.StringConstants
import com.example.weatherapp.viewmodel.SearchWeatherViewModel
import com.example.weatherapp.viewmodel.WeatherUiState

@Composable
fun SearchWeatherScreen(
    viewModel: SearchWeatherViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val location by viewModel.location.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) viewModel.fetchCurrentLocationNameAndWeather()
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    Scaffold { innerPadding ->
        val scrollState = rememberScrollState()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF81D4FA), Color(0xFF0288D1))
                    )
                )
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(30.dp))

                Text(
                    text = StringConstants.APP_TITLE,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )

                Spacer(Modifier.height(20.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = location,
                            onValueChange = { viewModel.onLocationChange(it) },
                            placeholder = { Text(StringConstants.LOCATION_PLACEHOLDER) },
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            singleLine = true,
                            shape = RoundedCornerShape(28.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Gray.copy(alpha = 0.6f),
                                unfocusedIndicatorColor = Color.Gray.copy(alpha = 0.3f),
                                unfocusedTextColor = Color.Black,
                                focusedTextColor = Color.Black,
                                cursorColor = Color.Black,
                                unfocusedPlaceholderColor = Color.Gray,
                                focusedPlaceholderColor = Color.Gray
                            )
                        )

                        IconButton(
                            onClick = { permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION) }
                        ) {
                            Icon(Icons.Filled.MyLocation, contentDescription = "Use current location")
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { viewModel.fetchWeatherSummary(location) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0288D1),
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ) {
                        Text(StringConstants.SHOW_WEATHER_BTN)
                    }

                    Button(
                        onClick = { navController.navigate(StringConstants.LOCATIONS_CATALOG_SCREEN) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0288D1),
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ) {
                        Text(StringConstants.LOCATIONS_CATALOG_BTN)
                    }
                }

                Spacer(Modifier.height(20.dp))

                when (uiState) {
                    WeatherUiState.Idle -> Text("")
                    WeatherUiState.Loading -> CircularProgressIndicator(color = Color.White)
                    is WeatherUiState.Error -> Text(
                        "Error: ${(uiState as WeatherUiState.Error).message}",
                        color = Color.Red
                    )
                    is WeatherUiState.Success -> {
                        val data = (uiState as WeatherUiState.Success).data
                        Column {
                            MainWeatherCard(
                                data = data,
                                modifier = Modifier.padding(top = 20.dp)
                            )

                            Spacer(Modifier.height(16.dp))

                            HourlyForecastCard(
                                data = data,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                Spacer(Modifier.height(40.dp))
            }
        }
    }
}
