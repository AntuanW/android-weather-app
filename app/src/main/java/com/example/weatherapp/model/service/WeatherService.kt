package com.example.weatherapp.model.service

import android.util.Log
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.model.dto.WeatherSummaryFactory
import com.example.weatherapp.model.dto.weather.WeatherSummary
import com.example.weatherapp.model.service.client.WeatherApiInterface
import com.example.weatherapp.model.service.response.WeatherResponse
import javax.inject.Inject

class WeatherService @Inject constructor(
    private val client: WeatherApiInterface,
    private val weatherSummaryFactory: WeatherSummaryFactory
) {

    companion object {
        const val API_KEY = BuildConfig.API_KEY
    }

    suspend fun getWeather(location: String, days: Int): WeatherSummary {
        val weatherResponse: WeatherResponse = client.getForecast(API_KEY, location, days)
        Log.i("WeatherService", "Retrieved weather for location: ${weatherResponse.location}")
        Log.d("WeatherService", "Weather: $weatherResponse")
        return weatherSummaryFactory.fromWeatherResponse(weatherResponse)
    }
}
