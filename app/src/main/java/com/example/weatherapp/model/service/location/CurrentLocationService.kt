package com.example.weatherapp.model.service.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.example.weatherapp.model.dto.location.LocationResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

class CurrentLocationService @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices
        .getFusedLocationProviderClient(context)

    private val geocoder = Geocoder(context, Locale.ENGLISH)

    private companion object {
        const val MAX_RESULTS = 1
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(onResult: (LocationResult?) -> Unit) {
        val cancellationTokenSource = CancellationTokenSource()

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location ->
            if (location != null) {
                onResult(LocationResult(
                    location.latitude,
                    location.longitude,
                    ""
                ))
            } else {
                onResult(null)
            }
        }.addOnFailureListener {
            onResult(null)
        }
    }

    fun getCityFromCoordinates(latitude: Double, longitude: Double, onResult: (String?) -> Unit) {
        geocoder.getFromLocation(
            latitude,
            longitude,
            MAX_RESULTS,
            object : Geocoder.GeocodeListener {
                override fun onGeocode(addresses: MutableList<Address>) {
                    val city = addresses.firstOrNull()?.locality
                        ?: addresses.firstOrNull()?.subAdminArea
                    onResult(city)
                }

                override fun onError(errorMessage: String?) {
                    onResult(null)
                }
            }
        )
    }
}