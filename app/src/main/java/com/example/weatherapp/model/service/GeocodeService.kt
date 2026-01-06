package com.example.weatherapp.model.service

import android.util.Log
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.model.service.client.GeocodeApiInterface
import com.example.weatherapp.model.service.response.location.GeocodeResponse
import javax.inject.Inject

class GeocodeService @Inject constructor(
    private val client: GeocodeApiInterface
) {

    companion object {
        const val API_KEY = BuildConfig.GEO_API_KEY
    }

    suspend fun getGeocodingForName(location: String, limit: Int = 7): List<GeocodeResponse> {
        val resp = client.getCoordinatesByName(API_KEY, location, limit)
        Log.i("GeocodeService", "Retrieved geocoding for location: $resp")
        return resp
    }

    suspend fun getReverseGeocoding(lat: String, lon: String, limit: Int = 1): List<GeocodeResponse> {
        val resp = client.getNameByCoordinates(API_KEY, lat, lon, limit)
        Log.i("GeocodeService", "Retrieved geocoding name for coordinates: $resp")
        return resp
    }
}