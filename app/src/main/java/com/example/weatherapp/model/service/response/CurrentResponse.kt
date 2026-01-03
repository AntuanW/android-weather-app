package com.example.weatherapp.model.service.response

import com.google.gson.annotations.SerializedName

data class CurrentResponse(

    @SerializedName("temp_c")
    val tempC: Double,

    @SerializedName("wind_kph")
    val windKph: Double,

    val condition: Condition,

    @SerializedName("air_quality")
    val airQuality: AirQuality,

    @SerializedName("feelslike_c")
    val feelsLikeC: Double,

    @SerializedName("chance_of_rain")
    val chanceOfRain: Int,

    @SerializedName("chance_of_snow")
    val chanceOfSnow: Int,

    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Long,
)