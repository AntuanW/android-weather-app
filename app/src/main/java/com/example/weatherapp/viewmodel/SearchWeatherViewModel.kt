package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.service.WeatherSummaryService
import com.example.weatherapp.model.service.location.CurrentLocationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

    @HiltViewModel
    class SearchWeatherViewModel @Inject constructor(
        private val weatherSummaryService: WeatherSummaryService,
        private val currentLocationService: CurrentLocationService
    ) : ViewModel() {

        private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
        val uiState = _uiState

        private val _location = MutableStateFlow("")
        val location: StateFlow<String> = _location

        fun fetchWeatherSummary(location: String) {
            viewModelScope.launch {
                _uiState.value = WeatherUiState.Loading
                try {
                    val summary = weatherSummaryService.getWeatherSummary(location)
                    _uiState.value = WeatherUiState.Success(summary)
                } catch (e: Exception) {
                    _uiState.value = WeatherUiState.Error("Failed to load weather data for $location")
                }
            }
        }

        fun onLocationChange(location: String) {
            _location.value = location
        }

        fun setError(msg: String) {
            _uiState.value = WeatherUiState.Error(msg)
        }

        fun fetchCurrentLocationName() {
            currentLocationService.getCurrentLocation { result ->
                if (result == null) {
                    _uiState.value = WeatherUiState.Error("Couldn't get current location")
                    return@getCurrentLocation
                }

                viewModelScope.launch {
                    try {
                        _location.value = "${result.latitude},${result.longitude}"
                    } catch (e: Exception) {
                        _uiState.value = WeatherUiState.Error("Failed to resolve your location")
                    }
                }
            }
        }
    }