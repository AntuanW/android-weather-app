package com.example.weatherapp.model.service.response

import com.google.gson.annotations.SerializedName

data class Hour(
    val time: String,

    @SerializedName("time_epoch")
    val timeEpoch: Long,

    val condition: Condition,

    @SerializedName("temp_c")
    val tempC: Double,

    @SerializedName("wind_kph")
    val windKph: Double,

    @SerializedName("air_quality")
    val airQuality: AirQuality,


)
