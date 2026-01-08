package com.example.weatherapp.model.service.response.weather

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

    val humidity: Int,

    @SerializedName("pressure_mb")
    val pressureMb: Int,

    @SerializedName("wind_dir")
    val windDir: String,

    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Long,
)