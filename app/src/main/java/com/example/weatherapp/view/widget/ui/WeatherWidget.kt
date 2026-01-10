package com.example.weatherapp.view.widget.ui

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.text.Text
import com.example.weatherapp.model.widget.WeatherWidgetRepository

class WeatherWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val repo = WeatherWidgetRepository(context)
        val last = repo.loadLocationAndWeather()

        provideContent {
            if (last == null) {
                Text("Loading weather…")
            } else {
                val (_, weather) = last
                WeatherWidgetContent(weather)
            }
        }
    }
}
