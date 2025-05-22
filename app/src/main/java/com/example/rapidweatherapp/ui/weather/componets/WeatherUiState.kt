package com.example.rapidweatherapp.ui.weather.componets

import com.example.rapidweatherapp.data.remote.WeatherResponse


data class WeatherUiState(
    val city: String? = null,
    val weather: WeatherResponse? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isDarkTheme: Boolean = false
)