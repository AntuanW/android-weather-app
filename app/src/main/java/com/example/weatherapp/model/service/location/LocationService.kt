package com.example.weatherapp.model.service.location

import android.util.Log
import com.example.weatherapp.model.dto.location.LocationResult
import com.example.weatherapp.model.service.GeocodeService
import com.example.weatherapp.model.service.response.location.GeocodeResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationService @Inject constructor(
    private val currentLocationService: CurrentLocationService,
    private val geocodeService: GeocodeService
) {
    suspend fun getCurrentCityName(): GeocodeResponse? {
        val coords = getCurrentCoordinates() ?: return null

        return try {
            geocodeService.getReverseGeocoding(
                coords.latitude.toString(),
                coords.longitude.toString(),
                limit = 1
            ).firstOrNull()
        } catch (e: Exception) {
            Log.i("LocationService", "Failed to geocode coordinates lat: ${coords.latitude}, lon: ${coords.longitude}: ${e.message}")
            GeocodeResponse(name = "Unknown", country = "Unknown", state = "Unknown", lat = coords.latitude, lon = coords.longitude)
        }
    }

    private suspend fun getCurrentCoordinates(): LocationResult? =
        suspendCancellableCoroutine { cont ->
            currentLocationService.getCurrentLocation { result ->
                cont.resume(result)
            }
        }
}