package com.example.weatherapp.model.dto.weather

data class HourWeatherSummary(
    val temperature: Temperature,
    val forecast: Forecast,
    val tempC: Double,
    val chanceOfRain: Int,
    val chanceOfSnow: Int,
    val iconUrl: String,
    val time: String,
    val timeEpoch: Long
)
