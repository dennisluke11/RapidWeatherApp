package com.example.rapidweatherapp.data.remote


import android.os.Build
import com.example.rapidweatherapp.utils.FlowConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WeatherRepository(private val api: WeatherApi) : IWeatherRepository {

    override suspend fun getForecastWeather(city: String, apiKey: String): WeatherResponse =
        withContext(Dispatchers.IO) {
            api.getForecastWeather(city, apiKey, days = FlowConstants.WEATHER_DAYS)
        }

    override suspend fun getPastWeekWeather(city: String, apiKey: String): WeatherResponse =
        withContext(Dispatchers.IO) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                throw UnsupportedOperationException("Only supported on API level 26+")
            }

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val endDate = LocalDate.now()
            val startDate = endDate.minusDays(6)

            api.getPastWeekWeather(
                location = city,
                apiKey = apiKey,
                startDate = startDate.format(formatter),
                endDate = endDate.format(formatter)
            )
        }
}