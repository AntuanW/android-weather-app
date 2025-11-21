package com.example.weatherapp.model.dto.weather

data class WeatherSummary(
    val airCondition: AirCondition,
    val temperature: Temperature,
    val forecast: Forecast,
    val tempC: Double,
    val chanceOfRain: Int,
    val chanceOfSnow: Int,
    val iconUrl: String,
)