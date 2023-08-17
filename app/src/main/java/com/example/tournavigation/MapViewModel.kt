package com.example.tournavigation

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import mu.KotlinLogging

class MapViewModel: ViewModel() {
    private val logger = KotlinLogging.logger {  }
    val state: MutableState<MapState> = mutableStateOf(
        MapState(
            lastKnownLocation = null,
        )
    )

    @SuppressLint("MissingPermission")
    fun getDeviceLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    state.value = state.value.copy(
                        lastKnownLocation = task.result,
                    )
                }
            }
        } catch (e: SecurityException) {
            logger.error { "Error getting device location: ${e.message}" }
        }
    }
}