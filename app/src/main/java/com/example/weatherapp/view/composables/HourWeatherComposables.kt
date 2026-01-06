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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherapp.model.dto.weather.HourWeatherSummary
import com.example.weatherapp.model.dto.weather.WeatherSummary
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun HourlyForecastCard(
    data: WeatherSummary,
    modifier: Modifier = Modifier
) {
    val now = data.weatherPerHour.firstOrNull() ?: return
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Brush.verticalGradient(listOf(Color(0xFF81D4FA), Color(0xFF0288D1))))
                .padding(20.dp)
        ) {
            Text(
                text = "Next 24 hours",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )

            Spacer(Modifier.height(12.dp))

            CurrentHourDetails(now, textColor = Color.White)

            Spacer(Modifier.height(16.dp))

            HorizontalDivider(color = Color.White.copy(alpha = 0.3f))

            Spacer(Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(data.weatherPerHour.take(24)) { hour ->
                    HourForecastItem(hour, formatter)
                }
            }
        }
    }
}

@Composable
fun HourForecastItem(hour: HourWeatherSummary, formatter: DateTimeFormatter) {
    val precipitation = maxOf(hour.chanceOfRain, hour.chanceOfSnow)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .widthIn(min = 64.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.15f))
            .padding(vertical = 8.dp, horizontal = 6.dp)
    ) {
        Text(
            text = Instant
                .ofEpochSecond(hour.timeEpoch)
                .atZone(ZoneId.systemDefault())
                .format(formatter),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )

        Spacer(Modifier.height(4.dp))

        WeatherIcon(
            iconUrl = hour.iconUrl,
            modifier = Modifier.size(32.dp)
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = "${hour.tempC}°C",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )

        Spacer(Modifier.height(2.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.WaterDrop,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = Color(0xFF0288D1)
            )

            Spacer(Modifier.width(2.dp))

            Text(
                text = "$precipitation%",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}
