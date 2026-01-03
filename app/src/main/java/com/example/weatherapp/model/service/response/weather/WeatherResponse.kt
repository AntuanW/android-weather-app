package com.example.weatherapp.model.service.response.weather

data class WeatherResponse(
    val location: LocationResponse,
    val current: CurrentResponse,
    val forecast: ForecastResponse
)