package com.example.weatherapp.model.widget

import android.content.Context
import android.util.Log
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weatherapp.model.service.WeatherSummaryService
import com.example.weatherapp.model.service.location.LocationService
import com.example.weatherapp.view.utils.buildLocationLabel
import com.example.weatherapp.view.widget.state.WeatherWidgetState
import com.example.weatherapp.view.widget.ui.WeatherWidget
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class WeatherWidgetWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val weatherSummaryService: WeatherSummaryService,
    private val widgetRepository: WeatherWidgetRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val data = widgetRepository.loadLocationAndWeather()
            ?: return Result.retry()

        val (location, _) = data

        return try {
            val summary = weatherSummaryService.getWeatherSummary(location.lat, location.lon)
            val state = WeatherWidgetState(
                locationName = buildLocationLabel(location),
                tempC = summary.tempC,
                forecast = summary.forecast,
                lastUpdated = summary.lastUpdatedEpoch,
                airCondition = summary.airCondition,
                downfall = maxOf(summary.chanceOfRain, summary.chanceOfRain)
            )
            widgetRepository.saveLocationAndWeather(location, state)
            WeatherWidget().updateAll(applicationContext)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
