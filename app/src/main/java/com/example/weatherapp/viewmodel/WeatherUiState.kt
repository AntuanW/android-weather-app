package com.example.weatherapp.viewmodel

import com.example.weatherapp.model.dto.weather.WeatherSummary
import com.example.weatherapp.model.service.response.location.GeocodeResponse

sealed class WeatherUiState {
    object Idle: WeatherUiState()
    object Loading: WeatherUiState()
    data class Success(val data: WeatherSummary): WeatherUiState()
    data class Error(val message: String): WeatherUiState()

    data class LocationPicker(val locations: List<GeocodeResponse>) : WeatherUiState()
}