package com.example.weatherapp.model.service

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.model.service.client.WeatherApiInterface
import javax.inject.Inject

class WeatherService @Inject constructor(
    private val client: WeatherApiInterface
) {

    companion object {
        const val API_KEY = BuildConfig.API_KEY
    }

    fun getWeather() {

    }
}
