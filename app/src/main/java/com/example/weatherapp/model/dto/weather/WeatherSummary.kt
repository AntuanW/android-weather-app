package com.example.weatherapp.model.dto.weather

import com.example.weatherapp.model.service.response.weather.AirQuality

data class WeatherSummary(
    val airCondition: AirCondition,
    val airQuality: AirQuality,
    val temperature: Temperature,
    val forecast: Forecast,
    val tempC: Double,
    val chanceOfRain: Int,
    val chanceOfSnow: Int,
    val humidity: Int,
    val pressureMb: Int,
    val windDir: String,
    val iconUrl: String,
    val lastUpdatedEpoch: Long,
    val weatherPerHour: List<HourWeatherSummary>,
)