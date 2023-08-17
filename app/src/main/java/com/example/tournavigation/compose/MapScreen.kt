package com.example.tournavigation.compose

import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.tournavigation.MapState
import com.example.tournavigation.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun ComposeMapScreen(
    mapState: MapState
) {
    val sydney = LatLng(-33.852, 151.211)
    val amsterdamTomTom = LatLng(52.37732, 4.90976)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(amsterdamTomTom, 10f)
    }

    val mapProperties = MapProperties(
        isMyLocationEnabled = mapState.lastKnownLocation != null,
    )

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = mapProperties,
            cameraPositionState = cameraPositionState
        ) {
            MapMarker(
                context = LocalContext.current,
                position = amsterdamTomTom,
                title = "Marker in Amsterdam",
                iconResourceId = R.drawable.ic_pin_background
            )
            MapMarker(
                context = LocalContext.current,
                position = sydney,
                title = "Marker in Sydney",
                iconResourceId = R.drawable.ic_pin_background
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    cameraPositionState.move(CameraUpdateFactory.newLatLng(sydney))
                }
            ) {
                Text(text = "Animate camera to Sydney")
            }

            SearchBar()
        }
    }
}
