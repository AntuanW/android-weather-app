package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.repository.location.LocationRepository
import com.example.weatherapp.model.service.GeocodeService
import com.example.weatherapp.model.service.WeatherSummaryService
import com.example.weatherapp.model.service.location.LocationService
import com.example.weatherapp.model.service.response.location.GeocodeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchWeatherViewModel @Inject constructor(
    private val weatherSummaryService: WeatherSummaryService,
    private val locationService: LocationService,
    private val geocodeService: GeocodeService,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val uiState: StateFlow<WeatherUiState> = _uiState

    private val _selectedLocation = MutableStateFlow<GeocodeResponse?>(null)
    val selectedLocation: StateFlow<GeocodeResponse?> = _selectedLocation

    private val _location = MutableStateFlow("")
    val location: StateFlow<String> = _location

    private val favourites = locationRepository.observeFavourites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val isFavourite: StateFlow<Boolean> = combine(_selectedLocation, favourites) { selected, favs ->
        selected?.let { geo ->
            favs.any { it.latitude == geo.lat && it.longitude == geo.lon }
        } ?: false
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

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
                    results.isEmpty() -> _uiState.value = WeatherUiState.Error("City not found")
                    results.size == 1 -> {
                        _selectedLocation.value = results.first()
                        fetchWeather(results.first())
                    }
                    else -> _uiState.value = WeatherUiState.LocationPicker(results)
                }

            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error("Geocoding failed: ${e.message}")
            }
        }
    }

    fun onLocationSelected(geo: GeocodeResponse) {
        viewModelScope.launch {
            _selectedLocation.value = geo
            fetchWeather(geo)
        }
    }

    fun dismissLocationPicker() {
        _uiState.value = WeatherUiState.Idle
    }

    private suspend fun fetchWeather(geo: GeocodeResponse) {
        try {
            _location.value = buildLocationLabel(geo)
            _selectedLocation.value = geo

            val summary = weatherSummaryService.getWeatherSummary(geo.lat, geo.lon)
            _uiState.value = WeatherUiState.Success(summary)

        } catch (e: Exception) {
            _uiState.value = WeatherUiState.Error("Weather load failed: ${e.message}")
        }
    }

    fun fetchCurrentLocationNameAndWeather() {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading

            val city: GeocodeResponse? = locationService.getCurrentCityName()
            if (city == null) {
                _uiState.value = WeatherUiState.Error("Failed to resolve your location")
                return@launch
            }

            try {
                _location.value = buildLocationLabel(city)
                _selectedLocation.value = city

                val summary = weatherSummaryService.getWeatherSummary(city.lat, city.lon)
                _uiState.value = WeatherUiState.Success(summary)

            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error("Weather load failed: ${e.message}")
            }
        }
    }

    fun toggleFavourite(currentGeo: GeocodeResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            val favs = locationRepository.findAll()
            Log.i("SearchWeatherViewModel", "DB: $favs")

            if (favourites.value.any { it.latitude == currentGeo.lat && it.longitude == currentGeo.lon }) {
                locationRepository.removeFavourite(currentGeo.lat, currentGeo.lon)
                Log.i("SearchWeatherViewModel", "Removed favourite: $currentGeo")
            } else {
                locationRepository.addFavourite(currentGeo)
                Log.i("SearchWeatherViewModel", "Added favourite: $currentGeo")
            }
        }
    }

    private fun buildLocationLabel(geo: GeocodeResponse): String {
        return listOfNotNull(geo.name, geo.state, geo.country).joinToString(", ")
    }
}

