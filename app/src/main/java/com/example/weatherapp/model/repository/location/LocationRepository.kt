package com.example.weatherapp.model.repository.location

import com.example.weatherapp.model.database.WeatherAppDatabase
import com.example.weatherapp.model.database.model.Location
import com.example.weatherapp.model.service.response.location.GeocodeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationRepository @Inject constructor(
    db: WeatherAppDatabase
) {
    private val dao = db.locationDao()

    fun observeFavourites(): Flow<List<Location>> = dao.findAll()

    suspend fun addFavourite(geo: GeocodeResponse) = withContext(Dispatchers.IO) {
        if (!dao.exists(geo.lat, geo.lon)) {
            dao.insert(
                Location(
                    latitude = geo.lat,
                    longitude = geo.lon,
                    name = listOfNotNull(geo.name, geo.state, geo.country).joinToString(", ")
                )
            )
        }
    }

    suspend fun removeFavourite(lat: Double, lon: Double) = withContext(Dispatchers.IO) {
        dao.deleteByCoords(lat, lon)
    }

    suspend fun findAll(): List<Location> = withContext(Dispatchers.IO) {
        dao.findAllFavs()
    }

    suspend fun isFavourite(geo: GeocodeResponse): Boolean {
        return dao.exists(geo.lat, geo.lon)
    }
}