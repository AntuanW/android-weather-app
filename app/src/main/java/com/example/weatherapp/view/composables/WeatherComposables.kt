package com.example.weatherapp.view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.model.dto.weather.AirCondition
import com.example.weatherapp.model.dto.weather.Forecast

@Composable
fun WeatherIcon(iconUrl: String, modifier: Modifier = Modifier) {
    Image(
        painter = rememberAsyncImagePainter("https:$iconUrl"),
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
fun WeatherCard(
    temp: Double,
    condition: AirCondition,
    forecast: Forecast,
    iconUrl: String,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.Card(
        modifier = modifier.padding(16.dp),
        shape = androidx.compose.material3.MaterialTheme.shapes.extraLarge
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            WeatherIcon(
                iconUrl = iconUrl,
                modifier = Modifier.size(96.dp)
            )

            Text(
                text = "${temp}°C",
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "Air Quality: $condition",
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = "Forecast: $forecast",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
