package com.example.weatherapp.model.service

import com.example.weatherapp.model.dto.weather.WeatherSummary
import com.example.weatherapp.model.service.response.WeatherResponse
import javax.inject.Inject

class WeatherSummaryService @Inject constructor(
    private val weatherService: WeatherService
) {

    suspend fun getWeatherSummary(location: String, days: Int = 2): WeatherSummary {
        return weatherService.getWeather(location, days)
    }
}