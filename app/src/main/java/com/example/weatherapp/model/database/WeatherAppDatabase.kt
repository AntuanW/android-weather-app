package com.example.weatherapp.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.model.database.dao.LocationDao
import com.example.weatherapp.model.database.model.Location

@Database(
    entities = [Location::class],
    version = 1
)
abstract class WeatherAppDatabase: RoomDatabase() {

    companion object {
        const val DB_NAME = "weather_app_database"
    }

    abstract fun locationDao(): LocationDao
}