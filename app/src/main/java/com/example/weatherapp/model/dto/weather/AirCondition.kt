package com.example.weatherapp.model.dto.weather

enum class AirCondition(
    private val value: String
) {
    VERY_GOOD("Very Good"),
    GOOD("Good"),
    OKAY("Okay"),
    PASSABLE("Passable"),
    UNHEALTHY("Unhealthy"),
    VERY_UNHEALTHY("Very Unhealthy");

    override fun toString(): String {
        return value
    }
}