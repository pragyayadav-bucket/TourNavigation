package com.example.tournavigation

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.tournavigation.ui.theme.TourNavigationTheme
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import mu.KotlinLogging

class MainActivity : ComponentActivity() {
    val logger = KotlinLogging.logger { }
    private val viewModel: LocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            TourNavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    composeGoogleMap()
                }
            }
        }
    }
}

@Composable
fun composeGoogleMap() {
    val sydney = LatLng(-33.852, 151.211)
    val amsterdamTomTom = LatLng(52.37732, 4.90976)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(amsterdamTomTom, 10f)
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
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
            modifier = Modifier.align(TopStart)
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

@Composable
fun MapMarker(
    context: Context,
    position: LatLng,
    title: String,
    @DrawableRes iconResourceId: Int
) {
    val icon = bitmapDescriptorFromVector(
        context, iconResourceId
    )
    Marker(
        position = position,
        title = title,
        draggable = true,
        //icon = icon,
    )
}

@Composable
fun SearchBar() {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Search") },
        modifier = Modifier.fillMaxWidth(0.3f).padding(16.dp),
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
    )
}

//https://www.boltuix.com/2022/11/add-custom-marker-to-google-maps-in.html

fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TourNavigationTheme {
       // Greeting("Android")
        composeGoogleMap()
    }
}