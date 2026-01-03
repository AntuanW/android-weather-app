package com.example.weatherapp.model.service.response.weather

data class LocationResponse(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double
)