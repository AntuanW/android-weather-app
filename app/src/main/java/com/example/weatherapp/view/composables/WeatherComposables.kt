package com.example.weatherapp.view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.model.dto.weather.HourWeatherSummary
import com.example.weatherapp.model.dto.weather.WeatherSummary
import com.example.weatherapp.view.utils.gradient
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun WeatherIcon(iconUrl: String, modifier: Modifier = Modifier) {
    Image(
        painter = rememberAsyncImagePainter("https:$iconUrl"),
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
fun MainWeatherCard(
    data: WeatherSummary,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .height(220.dp),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(data.forecast.gradient())
                .padding(24.dp)
        ) {

            Column(
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Text(
                    text = "${data.tempC}°C",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White
                )

                Text(
                    text = data.forecast.name.lowercase().replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White.copy(alpha = 0.9f)
                )

                Spacer(Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    WeatherChip("AQ", data.airCondition.toString())
                    WeatherChip("Rain", "${data.chanceOfRain}%")
                }
            }

            WeatherIcon(
                iconUrl = data.iconUrl,
                modifier = Modifier
                    .size(140.dp)
                    .align(Alignment.BottomEnd)
                    .alpha(0.35f)
            )
        }
    }
}


@Composable
fun HourlyForecastCard(
    data: WeatherSummary,
    modifier: Modifier = Modifier
) {
    val now = data.weatherPerHour.firstOrNull() ?: return
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            Text(
                text = "Next 24 hours",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(12.dp))

            CurrentHourDetails(now)

            Spacer(Modifier.height(16.dp))

            HorizontalDivider()

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
fun CurrentHourDetails(hour: HourWeatherSummary) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

        WeatherIcon(
            iconUrl = hour.iconUrl,
            modifier = Modifier.size(64.dp)
        )

        Spacer(Modifier.width(16.dp))

        Column {
            Text(
                text = Instant
                    .ofEpochSecond(hour.timeEpoch)
                    .atZone(ZoneId.systemDefault())
                    .format(formatter),
                style = MaterialTheme.typography.labelLarge
            )

            Text(
                text = "${hour.tempC}°C · ${hour.forecast}",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Rain ${hour.chanceOfRain}%  •  Snow ${hour.chanceOfSnow}%",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun HourForecastItem(hour: HourWeatherSummary, formatter: DateTimeFormatter) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .widthIn(min = 64.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = Instant
                .ofEpochSecond(hour.timeEpoch)
                .atZone(ZoneId.systemDefault())
                .format(formatter),
            style = MaterialTheme.typography.labelSmall
        )

        Spacer(Modifier.height(4.dp))

        WeatherIcon(
            iconUrl = hour.iconUrl,
            modifier = Modifier.size(32.dp)
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = "${hour.tempC}°C",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun WeatherChip(label: String, value: String) {
    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .background(Color.White.copy(alpha = 0.2f))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.labelMedium,
            color = Color.White.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White
        )
    }
}
