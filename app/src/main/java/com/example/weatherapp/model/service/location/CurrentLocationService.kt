package com.example.weatherapp.model.service.location

import android.annotation.SuppressLint
import android.content.Context
import com.example.weatherapp.model.dto.location.LocationResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CurrentLocationService @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices
        .getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(onResult: (LocationResult?) -> Unit) {
        val cancellationTokenSource = CancellationTokenSource()

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location ->
            if (location != null) {
                onResult(LocationResult(location.latitude, location.longitude))
            } else {
                onResult(null)
            }
        }.addOnFailureListener {
            onResult(null)
        }
    }
}