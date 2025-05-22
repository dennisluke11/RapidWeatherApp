package com.example.rapidweatherapp.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rapidweatherapp.data.remote.IWeatherRepository
import com.example.rapidweatherapp.data.remote.WeatherResponse
import com.example.rapidweatherapp.ui.weather.componets.WeatherUiState
import com.example.rapidweatherapp.utils.FlowConstants
import com.example.rapidweatherapp.utils.GeocodingFailedException
import com.example.rapidweatherapp.utils.LocationHelper
import com.example.rapidweatherapp.utils.LocationPermissionDeniedException
import com.example.rapidweatherapp.utils.LocationUnavailableException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: IWeatherRepository,
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState

    suspend fun fetchWeatherDirect(
        city: String,
        apiKey: String = FlowConstants.API_KEY
    ): WeatherResponse {
        return repository.getForecastWeather(city, apiKey)
    }

    suspend fun fetchPastWeekWeatherDirect(
        city: String,
        apiKey: String = FlowConstants.API_KEY
    ): WeatherResponse {
        return repository.getPastWeekWeather(city, apiKey)
    }

    private fun combineWeatherData(
        currentWeather: WeatherResponse,
        pastWeekWeather: WeatherResponse
    ): WeatherResponse {
        val pastDays = pastWeekWeather.forecast.forecastday
        val currentDays = currentWeather.forecast.forecastday
        val combinedForecastDays = pastDays + currentDays

        return currentWeather.copy(
            forecast = currentWeather.forecast.copy(
                forecastday = combinedForecastDays
            )
        )
    }

    fun loadCityFromLocation() {
        viewModelScope.launch {
            try {
                val city = locationHelper.getCurrentCity()
                if (city.isBlank()) throw Exception("Empty city name")

                _uiState.value =
                    _uiState.value.copy(city = city, isLoading = true, errorMessage = null)

                loadWeatherForCity(city)

            } catch (e: LocationPermissionDeniedException) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Location permission denied",
                    isLoading = false
                )
            } catch (e: LocationUnavailableException) {
                _uiState.value =
                    _uiState.value.copy(errorMessage = "Location unavailable", isLoading = false)
            } catch (e: GeocodingFailedException) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to get city from location",
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message ?: "Unknown error",
                    isLoading = false
                )
            }
        }
    }

    fun searchWeatherByCityName(city: String) {
        viewModelScope.launch {
            if (city.isBlank()) {
                _uiState.value = _uiState.value.copy(errorMessage = "City name cannot be empty")
                return@launch
            }

            _uiState.value = _uiState.value.copy(city = city, isLoading = true, errorMessage = null)

            try {
                loadWeatherForCity(city)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to fetch weather data for \"$city\".",
                    isLoading = false
                )
            }
        }
    }

    private suspend fun loadWeatherForCity(city: String) {
        val currentWeather = fetchWeatherDirect(city)
        val pastWeekWeather = fetchPastWeekWeatherDirect(city)
        val combinedWeather = combineWeatherData(currentWeather, pastWeekWeather)

        _uiState.value = _uiState.value.copy(
            weather = combinedWeather,
            isLoading = false,
            errorMessage = null
        )
    }
}
