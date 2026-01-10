package com.example.weatherapp.model.widget

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.weatherapp.model.dto.weather.AirCondition
import com.example.weatherapp.model.dto.weather.Forecast
import com.example.weatherapp.model.service.response.location.GeocodeResponse
import com.example.weatherapp.view.widget.state.WeatherWidgetState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class WeatherWidgetRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val Context.weatherWidgetDataStore by preferencesDataStore("weather_widget_data")
    }

    private val dataStore = context.weatherWidgetDataStore

    private val NAME = stringPreferencesKey("name")
    private val TEMP = doublePreferencesKey("temp")
    private val AIR_CONDITION = stringPreferencesKey("air_condition")
    private val downfall = intPreferencesKey("downfall")
    private val FORECAST = stringPreferencesKey("forecast")
    private val TIME = longPreferencesKey("time")
    private val LAT = doublePreferencesKey("lat")
    private val LON = doublePreferencesKey("lon")

    suspend fun saveLocationAndWeather(location: GeocodeResponse, state: WeatherWidgetState) {
        dataStore.edit {
            it[NAME] = state.locationName
            it[TEMP] = state.tempC
            it[FORECAST] = state.forecast.name
            it[TIME] = state.lastUpdated
            it[LAT] = location.lat
            it[LON] = location.lon
        }
    }

    suspend fun loadLocationAndWeather(): Pair<GeocodeResponse, WeatherWidgetState>? {
        val data = dataStore.data.first()
        val name = data[NAME] ?: return null
        val lat = data[LAT] ?: return null
        val lon = data[LON] ?: return null

        val weather = WeatherWidgetState(
            locationName = name,
            tempC = data[TEMP] ?: 0.0,
            forecast = Forecast.valueOf(data[FORECAST] ?: "CLEAR"),
            lastUpdated = data[TIME] ?: 0,
            downfall = data[downfall] ?: 0,
            airCondition = AirCondition.valueOf(data[AIR_CONDITION] ?: "OKAY")
        )

        val location = GeocodeResponse(name = name, lat = lat, lon = lon, country = null, state = null)
        return location to weather
    }
}
