package com.example.weatherapp.viewmodel

import com.example.weatherapp.model.database.model.Location
import com.example.weatherapp.model.dto.weather.WeatherSummary

data class FavouriteLocationUiState(
    val location: Location,
    val summary: WeatherSummary?
)
