package com.example.weatherapp.model.service

import com.example.weatherapp.model.dto.weather.WeatherSummary
import javax.inject.Inject

class WeatherSummaryService @Inject constructor(
    private val weatherService: WeatherService
) {

    suspend fun getWeatherSummary(lat: Double, lon: Double, days: Int = 2): WeatherSummary {
        return weatherService.getWeather("$lat,$lon", days)
    }
}