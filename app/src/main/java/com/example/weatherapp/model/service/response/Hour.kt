package com.example.weatherapp.model.service.response

import com.google.gson.annotations.SerializedName

data class Hour(
    val time: String,

    @SerializedName("time_epoch")
    val timeEpoch: Long,

    val condition: Condition,

    @SerializedName("temp_c")
    val tempC: Double,

    @SerializedName("feelslike_c")
    val feelsLikeC: Double,

    @SerializedName("chance_of_rain")
    val chanceOfRain: Int,

    @SerializedName("chance_of_snow")
    val chanceOfSnow: Int,

    @SerializedName("wind_kph")
    val windKph: Double,

    @SerializedName("air_quality")
    val airQuality: AirQuality,


)
