package com.example.weatherapp.view.widget.state

import com.example.weatherapp.model.dto.weather.Forecast

data class WeatherWidgetState(
    val locationName: String,
    val tempC: Double,
    val forecast: Forecast,
    val lastUpdated: Long
)
