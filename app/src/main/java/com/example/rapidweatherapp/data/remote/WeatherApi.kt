package com.example.rapidweatherapp.data.remote


import com.example.rapidweatherapp.utils.FlowConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast.json")
    suspend fun getForecastWeather(
        @Query("q") location: String,
        @Query("key") apiKey: String,
        @Query("days") days: Int = FlowConstants.WEATHER_DAYS
    ): WeatherResponse

    @GET("history.json")
    suspend fun getPastWeekWeather(
        @Query("q") location: String,
        @Query("key") apiKey: String,
        @Query("dt") startDate: String,
        @Query("end_dt") endDate: String
    ): WeatherResponse
}