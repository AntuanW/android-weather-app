package com.example.weatherapp.view.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherapp.model.database.model.Location
import com.example.weatherapp.model.service.response.location.GeocodeResponse

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
    location: Location,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(location.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    "${location.latitude}, ${location.longitude}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "Remove")
            }
        }
    }
}
