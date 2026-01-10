package com.example.weatherapp.view.widget.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.weatherapp.R
import com.example.weatherapp.view.utils.widgetBackground
import com.example.weatherapp.view.widget.state.WeatherWidgetState

@SuppressLint("RestrictedApi")
@Composable
fun WeatherWidgetContent(weather: WeatherWidgetState?) {
    if (weather == null) {
        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(ColorProvider(Color.DarkGray))
                .padding(16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                "Open Weather App",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = ColorProvider(R.color.white)
                )
            )
        }
        return
    }

    val bg = weather.forecast.widgetBackground()

    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(ImageProvider(bg))
            .padding(16.dp)
    ) {
        Column(
            verticalAlignment = Alignment.Vertical.CenterVertically,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            Text(
                text = weather.locationName,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = ColorProvider(R.color.white),
                    fontWeight = FontWeight.Medium
                )
            )

            Spacer(GlanceModifier.height(8.dp))

            Text(
                text = "${weather.tempC.toInt()}°C",
                style = TextStyle(
                    fontSize = 36.sp,
                    color = ColorProvider(R.color.white),
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(GlanceModifier.height(4.dp))

            Text(
                text = weather.forecast.name.replaceFirstChar { it.uppercase() },
                style = TextStyle(
                    fontSize = 14.sp,
                    color = ColorProvider(R.color.white)
                )
            )

            Spacer(GlanceModifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.Vertical.CenterVertically,
                horizontalAlignment = Alignment.Horizontal.CenterHorizontally
            ) {
                WeatherWidgetChip("AQ", weather.airCondition.toString())
                Spacer(GlanceModifier.width(8.dp))
                WeatherWidgetChip("Downfall", "${weather.downfall}%")
            }
        }
    }
}

@Composable
@SuppressLint("RestrictedApi")
fun WeatherWidgetChip(label: String, value: String) {
    Row(
        modifier = GlanceModifier
            .background(ColorProvider(R.color.transparent))
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .cornerRadius(12.dp),
        verticalAlignment = Alignment.Vertical.CenterVertically
    ) {
        Text(
            text = "$label: ",
            style = TextStyle(
                fontSize = 12.sp,
                color = ColorProvider(R.color.almost_white)
            )
        )

        Text(
            text = value,
            style = TextStyle(
                fontSize = 12.sp,
                color = ColorProvider(R.color.white)
            )
        )
    }
}
