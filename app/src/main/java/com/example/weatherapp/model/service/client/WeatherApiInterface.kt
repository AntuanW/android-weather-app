package com.example.weatherapp.model.service.client

import com.example.weatherapp.model.service.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiInterface {

    @GET("/forecast.json")
    suspend fun getForecast(
        @Query("key") key : String,
        @Query("q") location: String,
        @Query("days") days: Int = 1,
        @Query("aqi") aqi: String = "yes"
    ) : WeatherResponse
}