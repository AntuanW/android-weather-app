package com.example.weatherapp.model.dto

import android.util.Log
import com.example.weatherapp.model.dto.weather.AirCondition
import com.example.weatherapp.model.dto.weather.Forecast
import com.example.weatherapp.model.dto.weather.HourWeatherSummary
import com.example.weatherapp.model.dto.weather.Temperature
import com.example.weatherapp.model.dto.weather.WeatherSummary
import com.example.weatherapp.model.service.response.weather.AirQuality
import com.example.weatherapp.model.service.response.weather.ForecastDay
import com.example.weatherapp.model.service.response.weather.Hour
import com.example.weatherapp.model.service.response.weather.WeatherResponse
import java.time.Instant
import java.time.temporal.ChronoUnit

class WeatherSummaryFactory {

    private companion object {
        val FORECAST_MAP = mapOf(
            Forecast.CLEAR to listOf("clear", "sunny"),
            Forecast.FOGGY to listOf("fog", "mist"),
            Forecast.SLEETY to listOf("sleet"),
            Forecast.THUNDERY to listOf("thundery"),
            Forecast.CLOUDY to listOf("cloudy", "overcast"),
            Forecast.HAIL to listOf("ice pellets"),
            Forecast.SNOWY to listOf("snow", "blizzard"),
            Forecast.RAINY to listOf("rain", "drizzle"),
        )
    }

    fun fromWeatherResponse(weatherResponse: WeatherResponse): WeatherSummary {
        val realTemp: Double = weatherResponse.current.feelsLikeC
        val airCondition: AirCondition = processAirCondition(weatherResponse.current.airQuality.pm2_5)
        val airQuality: AirQuality = weatherResponse.current.airQuality
        val temperature: Temperature = processTemperature(realTemp)
        val forecast: Forecast = processForecast(weatherResponse.current.condition.text)
        val chanceOfRain: Int = weatherResponse.current.chanceOfRain
        val chanceOfSnow: Int = weatherResponse.current.chanceOfSnow
        val iconUrl: String = weatherResponse.current.condition.icon
        val hourly: List<HourWeatherSummary> = parseForecastHourly(weatherResponse.forecast.forecastDay)
        val lastUpdatedEpoch: Long = weatherResponse.current.lastUpdatedEpoch
        val humidity: Int = weatherResponse.current.humidity
        val pressureMb: Int = weatherResponse.current.pressureMb
        val windDir: String = weatherResponse.current.windDir
        val localtime: String = weatherResponse.location.localtime



        for (hour in hourly) {
            Log.d("WeatherSummaryFactory", "hour: $hour")
            Log.d("WeatherSummaryFactory", "lastUpdatedEpoch: $lastUpdatedEpoch")
        }
        return WeatherSummary(
            airCondition = airCondition,
            temperature = temperature,
            forecast = forecast,
            tempC = realTemp,
            chanceOfRain = chanceOfRain,
            chanceOfSnow = chanceOfSnow,
            iconUrl = iconUrl,
            weatherPerHour = hourly,
            lastUpdatedEpoch = lastUpdatedEpoch,
            airQuality = airQuality,
            humidity = humidity,
            pressureMb = pressureMb,
            windDir = windDir,
            localtime = localtime
        )
    }

    private fun parseForecastHourly(forecastDays: List<ForecastDay>): List<HourWeatherSummary> {
        val now = Instant.now().truncatedTo(ChronoUnit.HOURS)
        val currentTimeSeconds = now.minusSeconds(3600).epochSecond
        val maxTimeSeconds = now.plusSeconds(86400).epochSecond

        Log.i("WeatherSummaryFactory", "currentTimeSeconds $currentTimeSeconds 24h later $maxTimeSeconds")
        return forecastDays
            .flatMap { it.hour }
            .filter { it.timeEpoch in (currentTimeSeconds + 1)..<maxTimeSeconds }
            .map { getWeatherForHour(it) }

    }

    private fun getWeatherForHour(hour: Hour): HourWeatherSummary {
        val realTemp: Double = hour.feelsLikeC
        val temperature: Temperature = processTemperature(realTemp)
        val forecast: Forecast = processForecast(hour.condition.text)
        val chanceOfRain: Int = hour.chanceOfRain
        val chanceOfSnow: Int = hour.chanceOfSnow
        val iconUrl: String = hour.condition.icon
        return HourWeatherSummary(
            temperature = temperature,
            forecast = forecast,
            tempC = realTemp,
            chanceOfRain = chanceOfRain,
            chanceOfSnow = chanceOfSnow,
            iconUrl = iconUrl,
            time = hour.time,
            timeEpoch = hour.timeEpoch
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
}