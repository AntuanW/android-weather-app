package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.dto.weather.WeatherSummary
import com.example.weatherapp.model.service.WeatherSummaryService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherSummaryService: WeatherSummaryService
) : ViewModel() {

    private val _weatherSummary = MutableStateFlow<WeatherSummary?>(null)
    val weatherSummary: StateFlow<WeatherSummary?> = _weatherSummary

    fun fetchWeatherSummary(location: String) {
        viewModelScope.launch {
            _weatherSummary.value = weatherSummaryService.getWeatherSummary(location)
        }
    }
}