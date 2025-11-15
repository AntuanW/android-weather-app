package com.example.weatherapp.model.service.response

import com.google.gson.annotations.SerializedName

data class ForecastDay(

    val date: String,

    @SerializedName("date_epoch")
    val dateEpoch: Long,

    val day: Day,

    @SerializedName("air_quality")
    val airQuality: AirQuality,

    val hour: List<Hour>,
)