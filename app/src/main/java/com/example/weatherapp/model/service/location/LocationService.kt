package com.example.weatherapp.model.service.location

import android.util.Log
import com.example.weatherapp.model.dto.location.LocationResult
import com.example.weatherapp.model.service.GeocodeService
import com.example.weatherapp.model.service.response.location.GeocodeResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationService @Inject constructor(
    private val currentLocationService: CurrentLocationService,
    private val geocodeService: GeocodeService
) {
    suspend fun getCurrentCityName(): LocationResult? {
        val coords = getCurrentCoordinates() ?: return null

        return try {
            val geocoding = geocodeService.getReverseGeocoding(
                coords.latitude.toString(),
                coords.longitude.toString(),
                limit = 1
            ).firstOrNull()

            LocationResult(
                latitude = coords.latitude,
                longitude = coords.longitude,
                name = buildCityLabel(geocoding)
            )
        } catch (e: Exception) {
            Log.i("LocationService", "Failed to geocode coordinates lat: ${coords.latitude}, lon: ${coords.longitude}: ${e.message}")
            LocationResult(coords.latitude, coords.longitude, "")
        }
    }

    private suspend fun getCurrentCoordinates(): LocationResult? =
        suspendCancellableCoroutine { cont ->
            currentLocationService.getCurrentLocation { result ->
                cont.resume(result)
            }
        }

    private fun buildCityLabel(geo: GeocodeResponse?): String {
        if (geo == null) return ""

        return listOfNotNull(
            geo.name,
            geo.state,
            geo.country
        ).joinToString(", ")
    }
}