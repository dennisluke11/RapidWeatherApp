package com.example.rapidweatherapp.data.remote


interface IWeatherRepository {
    suspend fun getForecastWeather(city: String, apiKey: String): WeatherResponse
    suspend fun getPastWeekWeather(city: String, apiKey: String): WeatherResponse
}