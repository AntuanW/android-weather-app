package com.example.weatherapp.model.service.response

import com.google.gson.annotations.SerializedName

data class Day(

    @SerializedName("maxtemp_c")
    val maxTempC: Double,

    @SerializedName("mintemp_c")
    val minTempC: Double,

    @SerializedName("avgtemp_c")
    val avgTempC: Double,

    @SerializedName("daily_chance_of_rain")
    val dailyChanceOfRain: Int,

    @SerializedName("daily_chance_of_snow")
    val dailyChanceOfSnow: Int,

    @SerializedName("air_quality")
    val airQuality: AirQuality,

    val condition: Condition
)
