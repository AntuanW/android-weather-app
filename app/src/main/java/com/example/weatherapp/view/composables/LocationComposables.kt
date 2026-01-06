package com.example.weatherapp.view.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.weatherapp.model.service.response.location.GeocodeResponse
import com.example.weatherapp.view.utils.buildLocationLabel
import com.example.weatherapp.view.utils.gradient
import com.example.weatherapp.viewmodel.FavouriteLocationUiState

@Composable
fun FavouriteStar(
    geo: GeocodeResponse?,
    isFavourite: Boolean,
    onToggleFavourite: (GeocodeResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    geo ?: return

    val starColor by animateColorAsState(
        targetValue = if (isFavourite) Color(0xFFFFD700) else Color.Gray,
        animationSpec = tween(durationMillis = 300)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 4.dp)
            .animateContentSize(),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            onClick = { onToggleFavourite(geo) },
            modifier = Modifier
                .padding(end = 4.dp)
                .then(Modifier)
        ) {
            Icon(
                imageVector = if (isFavourite) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = if (isFavourite) "Remove from favourites" else "Add to favourites",
                tint = starColor,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun FavouriteLocationRow(
    item: FavouriteLocationUiState,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    val location = item.location
    val weather = item.summary

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(
                    weather?.forecast?.gradient()
                        ?: Brush.verticalGradient(listOf(Color(0xFF81D4FA), Color(0xFF0288D1)))
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = buildLocationLabel(location),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )

                if (weather?.localtime != null) {
                    Text(
                        "Local time: ${weather.localtime}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }

                Text(
                    String.format("%.4f, %.4f", location.latitude, location.longitude),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f)
                )

                if (weather != null) {
                    Text(
                        weather.forecast.toString(),
                        color = Color.White.copy(alpha = 0.9f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            if (weather != null) {
                AsyncImage(
                    model = "https:${weather.iconUrl}",
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 8.dp)
                )
            }

            if (weather != null) {
                Text(
                    "${weather.tempC.toInt()}°C",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
            }

            IconButton(onClick = onRemove) {
                Icon(
                    Icons.Default.Delete,
                    tint = Color.White,
                    contentDescription = "Remove"
                )
            }
        }
    }
}
