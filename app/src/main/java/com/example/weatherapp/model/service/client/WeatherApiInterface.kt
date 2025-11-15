package com.example.weatherapp.model.service.client

import com.example.weatherapp.model.service.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiInterface {

    @GET("/forecast.json")
    fun getForecast(
        @Query("key") key : String,
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("aqi") aqi: String
    ) : WeatherResponse
}