package com.example.tournavigation.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar() {
    var text by remember { mutableStateOf("") }

    @OptIn(ExperimentalMaterial3Api::class)
    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Search") },
        modifier = Modifier
            .fillMaxWidth(0.3f)
            .padding(16.dp),
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
    )
}
