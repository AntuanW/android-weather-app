package com.example.weatherapp.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.InvertColors
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.weatherapp.model.dto.weather.WeatherSummary

@Composable
fun WindAndAtmosphereCard(data: WeatherSummary, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(Color(0xFF81D4FA), Color(0xFF0288D1))))
                .padding(16.dp)
        ) {
            Text("Wind & Atmosphere", style = MaterialTheme.typography.titleLarge, color = Color.White)

            Spacer(Modifier.height(12.dp))

            val items = listOf(
                Triple("Humidity", "${data.humidity}%", Icons.Filled.InvertColors),
                Triple("Pressure", "${data.pressureMb} hPa", Icons.Filled.Speed),
                Triple("Wind", data.windDir, Icons.Filled.Air),
                Triple("Rain", "${data.chanceOfRain}%", Icons.Filled.Umbrella),
                Triple("Snow", "${data.chanceOfSnow}%", Icons.Filled.AcUnit),
                Triple("Temp", "${data.tempC}°C", Icons.Filled.Thermostat)
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                for (row in items.chunked(3)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for (item in row) {
                            AtmosphereItem(item.first, item.second, item.third, Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AtmosphereItem(label: String, value: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.15f))
            .padding(vertical = 8.dp)
    ) {
        Icon(icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(28.dp))
        Spacer(Modifier.height(4.dp))
        Text(label, color = Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.bodySmall)
        Text(value, color = Color.White, style = MaterialTheme.typography.bodyMedium)
    }
}
