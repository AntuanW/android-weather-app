package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.service.WeatherSummaryService
import com.example.weatherapp.model.service.location.LocationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchWeatherViewModel @Inject constructor(
    private val weatherSummaryService: WeatherSummaryService,
    private val locationService: LocationService
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val uiState: StateFlow<WeatherUiState> = _uiState

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

    fun onLocationChange(value: String) {
        _location.value = value
    }

    fun setError(msg: String) {
        _uiState.value = WeatherUiState.Error(msg)
    }

    fun fetchCurrentLocationNameAndWeather() {
        viewModelScope.launch {
            val city = locationService.getCurrentCityName()

            if (city == null) {
                _uiState.value = WeatherUiState.Error("Failed to resolve your location")
            } else {
                _location.value = city
                fetchWeatherSummary(city)
            }

            Log.i("SearchWeatherViewModel", "Current location: ${location.value}")
        }
    }
}