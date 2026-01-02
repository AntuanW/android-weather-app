package com.example.weatherapp.model.service.location

import com.example.weatherapp.model.dto.location.LocationResult
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationService @Inject constructor(
    private val currentLocationService: CurrentLocationService
) {
    suspend fun getCurrentCityName(): LocationResult? = suspendCoroutine { cont ->

        currentLocationService.getCurrentLocation { result ->
            if (result == null) {
                cont.resume(null)
                return@getCurrentLocation
            }

            currentLocationService.getCityFromCoordinates(
                result.latitude,
                result.longitude
            ) { city ->
                val location = LocationResult(result.latitude, result.longitude, city ?: "")
                cont.resume(location)
            }
        }
    }
}