package com.example.weatherapp.view.widget.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
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
        Text("Open Weather App")
        return
    }

    val bg = weather.forecast.widgetBackground()

    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(ImageProvider(bg))
            .padding(16.dp)
    ) {
        Column {
            Text(
                weather.locationName,
                style = TextStyle(fontSize = 16.sp, color = ColorProvider(R.color.white))
            )

            Text(
                "${weather.tempC.toInt()}°C",
                style = TextStyle(fontSize = 36.sp, color = ColorProvider(R.color.white))
            )

            Text(
                weather.forecast.name,
                style = TextStyle(fontSize = 14.sp, color = ColorProvider(R.color.white))
            )
        }
    }
}
