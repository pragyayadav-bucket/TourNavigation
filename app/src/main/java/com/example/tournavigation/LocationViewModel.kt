package com.example.tournavigation

import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng

// This class will be the state holder for LocationsState and will be used to communicate between
// the UI and the ViewModel. Also this will be responsible for fetching and updating the current
// location.
// Reference : https://developer.android.com/training/location/retrieve-current

class LocationViewModel: ViewModel() {

    lateinit var fusedLocationClient: FusedLocationProviderClient

    var locationState by mutableStateOf<LocationState>(LocationState.NoPermission)  //= mutableStateOf<LocationState>(LocationState.NoPermission)

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        locationState = LocationState.LocationLoading

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).addOnSuccessListener { location: Location? ->
            locationState = if (location == null && locationState !is LocationState.LocationAvailable) {
                LocationState.Error
            } else {
                LocationState.LocationAvailable(LatLng(location!!.latitude, location.longitude))
            }
        }.addOnFailureListener { exception: Exception ->
            locationState = LocationState.Error
        }
    }

}