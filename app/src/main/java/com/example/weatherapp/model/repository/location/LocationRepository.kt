package com.example.weatherapp.model.repository.location

import com.example.weatherapp.model.database.WeatherAppDatabase
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val db: WeatherAppDatabase
) {
}