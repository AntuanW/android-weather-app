package com.example.weatherapp.view.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.weatherapp.model.database.model.Location
import com.example.weatherapp.model.dto.weather.Forecast
import com.example.weatherapp.model.service.response.location.GeocodeResponse

fun Forecast.gradient(): Brush {
    return when (this) {
        Forecast.CLEAR -> Brush.verticalGradient(
            listOf(Color(0xFF4FC3F7), Color(0xFF81D4FA))
        )
        Forecast.CLOUDY -> Brush.verticalGradient(
            listOf(Color(0xFF90A4AE), Color(0xFFCFD8DC))
        )
        Forecast.RAINY, Forecast.THUNDERY -> Brush.verticalGradient(
            listOf(Color(0xFF455A64), Color(0xFF1C313A))
        )
        Forecast.SNOWY -> Brush.verticalGradient(
            listOf(Color(0xFFE3F2FD), Color.White)
        )
        else -> Brush.verticalGradient(
            listOf(Color(0xFFB0BEC5), Color(0xFFECEFF1))
        )
    }
}

fun buildLocationLabel(geo: GeocodeResponse): String {
    return listOfNotNull(geo.name, geo.state, geo.country).joinToString(", ")
}

fun buildLocationLabel(location: Location): String {
    return listOfNotNull(location.name, location.state, location.country).joinToString(", ")
}
