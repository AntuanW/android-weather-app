package com.example.weatherapp.view.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.weatherapp.model.service.response.location.GeocodeResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPickerDialog(
    locations: List<GeocodeResponse>,
    onSelect: (GeocodeResponse) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Text(
                text = "Select location",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(12.dp))

            LazyColumn {
                items(locations) { location ->
                    LocationRow(
                        location = location,
                        onClick = { onSelect(location) }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun LocationRow(
    location: GeocodeResponse,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(14.dp)
    ) {
        Text(
            text = location.name ?: "Unknown",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = buildString {
                val state = location.state?.trim().takeIf { !it.isNullOrEmpty() }
                val country = location.country?.trim().takeIf { !it.isNullOrEmpty() }

                if (state != null) {
                    append(state)
                    if (country != null) append(", ")
                }

                append(country ?: "")
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = "${location.lat}, ${location.lon}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}
