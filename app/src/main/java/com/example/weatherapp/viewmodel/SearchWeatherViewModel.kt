package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.dto.location.LocationResult
import com.example.weatherapp.model.service.GeocodeService
import com.example.weatherapp.model.service.WeatherSummaryService
import com.example.weatherapp.model.service.location.LocationService
import com.example.weatherapp.model.service.response.location.GeocodeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchWeatherViewModel @Inject constructor(
    private val weatherSummaryService: WeatherSummaryService,
    private val locationService: LocationService,
    private val geocodeService: GeocodeService
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val uiState: StateFlow<WeatherUiState> = _uiState

    private val _location = MutableStateFlow("")
    val location: StateFlow<String> = _location

    fun onLocationChange(value: String) {
        _location.value = value
    }

    fun searchLocation() {
        val query = _location.value.trim()
        if (query.isEmpty()) return

        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading

            try {
                val results = geocodeService.getGeocodingForName(query)

                when {
                    results.isEmpty() -> {
                        _uiState.value = WeatherUiState.Error("City not found")
                    }

                    results.size == 1 -> {
                        fetchWeather(results.first())
                    }

                    else -> {
                        _uiState.value = WeatherUiState.LocationPicker(results)
                    }
                }

            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error("Geocoding failed: ${e.message}")
            }
        }
    }

    fun onLocationSelected(geo: GeocodeResponse) {
        viewModelScope.launch {
            fetchWeather(geo)
        }
    }

    fun dismissLocationPicker() {
        _uiState.value = WeatherUiState.Idle
    }

    private suspend fun fetchWeather(geo: GeocodeResponse) {
        try {
            _location.value = buildLocationLabel(geo)

            val summary = weatherSummaryService.getWeatherSummary(
                geo.lat,
                geo.lon
            )

            _uiState.value = WeatherUiState.Success(summary)

        } catch (e: Exception) {
            _uiState.value = WeatherUiState.Error("Weather load failed: ${e.message}")
        }
    }

    private fun buildLocationLabel(geo: GeocodeResponse): String {
        return listOfNotNull(
            geo.name,
            geo.state,
            geo.country
        ).joinToString(", ")
    }

    fun fetchCurrentLocationNameAndWeather() {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading

            val city: LocationResult? = locationService.getCurrentCityName()

            if (city == null) {
                _uiState.value = WeatherUiState.Error("Failed to resolve your location")
                return@launch
            }

            try {
                val summary = weatherSummaryService.getWeatherSummary(
                    city.latitude,
                    city.longitude
                )

                _location.value = city.name
                _uiState.value = WeatherUiState.Success(summary)

                Log.i("SearchWeatherViewModel", "Current location: ${city.name}")

            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error("Weather load failed: ${e.message}")
            }
        }
    }
}
