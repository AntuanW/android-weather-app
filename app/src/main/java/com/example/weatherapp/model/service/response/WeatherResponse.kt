package com.example.weatherapp.model.service.response

data class WeatherResponse(
    val location: LocationResponse,
    val current: CurrentResponse,
    val forecast: ForecastResponse
)