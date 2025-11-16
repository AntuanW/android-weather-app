package com.example.weatherapp.model.dto

import com.example.weatherapp.model.dto.weather.AirCondition
import com.example.weatherapp.model.dto.weather.Forecast
import com.example.weatherapp.model.dto.weather.Temperature
import com.example.weatherapp.model.dto.weather.WeatherSummary
import com.example.weatherapp.model.service.response.WeatherResponse
import kotlin.math.pow
import kotlin.math.round

class WeatherSummaryFactory {

    private companion object {
        const val BASE: Double = 13.12
        const val TEMPERATURE_CONSTANT: Double = 0.6215
        const val WIND_SPEED_CONSTANT_1: Double = 11.37
        const val WIND_SPEED_CONSTANT_2: Double = 0.16
        const val WIND_CHILL_CONSTANT: Double = 0.3965

        val FORECAST_MAP = mapOf(
            Forecast.CLEAR to listOf("clear", "sunny"),
            Forecast.FOGGY to listOf("fog", "mist"),
            Forecast.SLEETY to listOf("sleet"),
            Forecast.THUNDERY to listOf("thundery"),
            Forecast.CLOUDY to listOf("cloudy", "overcast"),
            Forecast.HAIL to listOf("ice pellets"),
            Forecast.SNOWY to listOf("snow", "blizzard"),
            Forecast.RAINY to listOf("rain"),
        )
    }

    fun fromWeatherResponse(weatherResponse: WeatherResponse): WeatherSummary {
        val realTemp: Double = calculateRealTempC(
            weatherResponse.current.tempC,
            weatherResponse.current.windKph
        )

        val airCondition: AirCondition = processAirCondition(weatherResponse.current.airQuality.pm2_5)
        val temperature: Temperature = processTemperature(realTemp)
        val forecast: Forecast = processForecast(weatherResponse.current.condition.text)
        val chanceOfRain: Int = weatherResponse.current.chanceOfRain
        val chanceOfSnow: Int = weatherResponse.current.chanceOfSnow


        return WeatherSummary(
            airCondition = airCondition,
            temperature = temperature,
            forecast = forecast,
            tempC = realTemp,
            chanceOfRain = chanceOfRain,
            chanceOfSnow = chanceOfSnow
        )
    }

    private fun processAirCondition(airCondition: Double): AirCondition {
        return when {
            airCondition < 12 -> AirCondition.VERY_GOOD
            airCondition < 36 -> AirCondition.GOOD
            airCondition < 60 -> AirCondition.OKAY
            airCondition < 84 -> AirCondition.PASSABLE
            airCondition < 180 -> AirCondition.UNHEALTHY
            else -> AirCondition.VERY_UNHEALTHY
        }
    }

    private fun processTemperature(temperature: Double): Temperature {
        return when {
            temperature < 3 -> Temperature.FREEZING
            temperature < 12 -> Temperature.COLD
            temperature < 17 -> Temperature.MILD
            temperature < 22 -> Temperature.WARM
            temperature < 27 -> Temperature.HOT
            else -> Temperature.SWELTERING
        }
    }

    private fun processForecast(forecast: String): Forecast {
        return FORECAST_MAP.entries.firstOrNull {
            (_, keys) -> keys.any { it in forecast.lowercase() }
        }?.key ?: Forecast.UNKNOWN
    }

    private fun calculateRealTempC(tempC: Double, windKph: Double): Double {
        val windFactor = windKph.pow(WIND_SPEED_CONSTANT_2)

        val sensedTemperature = BASE
        + TEMPERATURE_CONSTANT * tempC
        - WIND_SPEED_CONSTANT_1 * windFactor
        + WIND_CHILL_CONSTANT * tempC * windFactor

        return round(sensedTemperature * 1000) / 1000
    }
}