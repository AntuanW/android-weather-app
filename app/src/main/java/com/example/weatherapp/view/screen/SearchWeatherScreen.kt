package com.example.weatherapp.view.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.weatherapp.view.composables.FavouriteStar
import com.example.weatherapp.view.composables.HourlyForecastCard
import com.example.weatherapp.view.composables.LocationPickerDialog
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
    val selectedLocation by viewModel.selectedLocation.collectAsState()
    val isFavourite by viewModel.isFavourite.collectAsState()

    var showRemoveDialog by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) viewModel.fetchCurrentLocationNameAndWeatherIfNeeded()
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
                    Brush.verticalGradient(
                        listOf(Color(0xFF81D4FA), Color(0xFF0288D1))
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(30.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color(0xFFE3F2FD), Color(0xFFBBDEFB))
                                )
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = location,
                            onValueChange = viewModel::onLocationChange,
                            placeholder = {
                                Text(
                                    StringConstants.LOCATION_PLACEHOLDER,
                                    color = Color(0x88000000)
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            singleLine = true,
                            shape = RoundedCornerShape(24.dp),

                            trailingIcon = {
                                if (location.isNotEmpty()) {
                                    IconButton(onClick = { viewModel.clearLocation() }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Clear",
                                            tint = Color(0xFF0288D1)
                                        )
                                    }
                                }
                            },

                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = Color(0xFF0288D1),
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            )
                        )

                        IconButton(
                            onClick = {
                                permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)

                                viewModel.toggleCurrentLocation()
                                viewModel.fetchCurrentLocationNameAndWeatherIfNeeded()
                            },
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(
                                        color = Color.White.copy(alpha = 0.25f),
                                        shape = CircleShape
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.MyLocation,
                                    contentDescription = "Use current location",
                                    tint = Color(0xFF0288D1),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { viewModel.searchLocation() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0288D1),
                            contentColor = Color.White
                        )
                    ) {
                        Text(StringConstants.SHOW_WEATHER_BTN)
                    }

                    Button(
                        onClick = {
                            navController.navigate(StringConstants.LOCATIONS_CATALOG_SCREEN)
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0288D1),
                            contentColor = Color.White
                        )
                    ) {
                        Text(StringConstants.LOCATIONS_CATALOG_BTN)
                    }
                }

                Spacer(Modifier.height(20.dp))

                when (uiState) {
                    WeatherUiState.Idle -> {}

                    WeatherUiState.Loading ->
                        CircularProgressIndicator(color = Color.White)

                    is WeatherUiState.Error ->
                        Text(
                            text = (uiState as WeatherUiState.Error).message,
                            color = Color.Red
                        )

                    is WeatherUiState.Success -> {
                        val data = (uiState as WeatherUiState.Success).data

                        Column(
                            modifier = Modifier.background(
                                color = Color(0x33FFFFFF),
                                shape = RoundedCornerShape(16.dp)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = selectedLocation?.let { "${it.name}, ${it.country}" } ?: "",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                FavouriteStar(
                                    geo = selectedLocation,
                                    isFavourite = isFavourite,
                                    onToggleFavourite = { geo ->
                                        if (isFavourite) {
                                            showRemoveDialog = true
                                        } else {
                                            viewModel.toggleFavourite(geo)
                                        }
                                    },
                                    modifier = Modifier
                                        .background(
                                            color = Color(0x66FFFFFF),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .padding(4.dp)
                                )
                            }

                            Spacer(Modifier.height(8.dp))

                            MainWeatherCard(
                                data = data,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(Modifier.height(16.dp))

                            HourlyForecastCard(
                                data = data,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    is WeatherUiState.LocationPicker -> {
                        LocationPickerDialog(
                            locations = (uiState as WeatherUiState.LocationPicker).locations,
                            onSelect = viewModel::onLocationSelected,
                            onDismiss = viewModel::dismissLocationPicker
                        )
                    }
                }

                Spacer(Modifier.height(40.dp))
            }
        }
    }

    if (showRemoveDialog && selectedLocation != null) {
        AlertDialog(
            onDismissRequest = { showRemoveDialog = false },
            title = { Text("Remove from favourites?") },
            text = {
                Text("Do you really want to remove ${selectedLocation!!.name} from your favourites?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.toggleFavourite(selectedLocation!!)
                        showRemoveDialog = false
                    }
                ) {
                    Text("Remove")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showRemoveDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
