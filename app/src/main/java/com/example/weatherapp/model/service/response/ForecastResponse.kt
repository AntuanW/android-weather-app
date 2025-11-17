package com.example.weatherapp.model.service.response

import com.google.gson.annotations.SerializedName


data class ForecastResponse(

    @SerializedName("forecastday")
    val forecastDay: List<ForecastDay>
)
