package com.example.weatherapp.model.repository.wardrobe

import com.example.weatherapp.model.database.WeatherAppDatabase
import javax.inject.Inject

class WardrobeRepository @Inject constructor(
    private val db: WeatherAppDatabase
) {
}