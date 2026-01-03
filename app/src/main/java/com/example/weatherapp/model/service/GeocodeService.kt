package com.example.weatherapp.model.service

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

    suspend fun getGeocodingForName(location: String): List<GeocodeResponse> {
        return client.getCoordinatesByName(API_KEY, location)
    }

    suspend fun getReverseGeocoding(lat: String, lon: String, limit: Int = 1): List<GeocodeResponse> {
        return client.getNameByCoordinates(API_KEY, lat, lon, limit)
    }
}