package com.example.weatherapp.view.widget.ui

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weatherapp.model.widget.WeatherWidgetWorker
import java.util.concurrent.TimeUnit

class WeatherWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = WeatherWidget()

    override fun onEnabled(context: Context) {
        super.onEnabled(context)

        val immediateWork = OneTimeWorkRequestBuilder<WeatherWidgetWorker>().build()
        WorkManager.getInstance(context).enqueue(immediateWork)

        val periodicWork = PeriodicWorkRequestBuilder<WeatherWidgetWorker>(
            15, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "weather_widget_worker",
                ExistingPeriodicWorkPolicy.UPDATE,
                periodicWork
            )
    }

    override fun onDisabled(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork("weather_widget_worker")
    }
}
