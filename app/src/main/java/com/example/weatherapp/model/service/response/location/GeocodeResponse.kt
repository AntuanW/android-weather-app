package com.example.weatherapp.model.service.response.location

data class GeocodeResponse(
    val name: String?,
    val lat: Double,
    val lon: Double,
    val country: String?,
    val state: String?
)
