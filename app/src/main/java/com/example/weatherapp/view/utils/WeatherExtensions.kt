package com.example.weatherapp.view.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.weatherapp.model.database.model.Location
import com.example.weatherapp.model.dto.weather.AirCondition
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

fun AirCondition.gradient(): Brush {
    return when (this) {

        AirCondition.VERY_GOOD -> Brush.verticalGradient(
            listOf(
                Color(0xFF2E7D32),
                Color(0xFF66BB6A)
            )
        )
        AirCondition.GOOD -> Brush.verticalGradient(
            listOf(
                Color(0xFF558B2F),
                Color(0xFF9CCC65)
            )
        )
        AirCondition.OKAY -> Brush.verticalGradient(
            listOf(
                Color(0xFFF9A825),
                Color(0xFFFFEB3B)
            )
        )
        AirCondition.PASSABLE -> Brush.verticalGradient(
            listOf(
                Color(0xFFF57C00),
                Color(0xFFFFA726)
            )
        )
        AirCondition.UNHEALTHY -> Brush.verticalGradient(
            listOf(
                Color(0xFFD32F2F),
                Color(0xFFEF5350)
            )
        )
        AirCondition.VERY_UNHEALTHY -> Brush.verticalGradient(
            listOf(
                Color(0xFF4E342E),
                Color(0xFF8D6E63)
            )
        )
    }
}

fun buildLocationLabel(geo: GeocodeResponse): String {
    return listOfNotNull(geo.name, geo.state, geo.country).joinToString(", ")
}

fun buildLocationLabel(location: Location): String {
    return listOfNotNull(location.name, location.state, location.country).joinToString(", ")
}
