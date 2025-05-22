package com.example.rapidweatherapp.di

import com.example.rapidweatherapp.data.remote.IWeatherRepository
import com.example.rapidweatherapp.data.remote.WeatherApi
import com.example.rapidweatherapp.data.remote.WeatherRepository
import com.example.rapidweatherapp.ui.weather.WeatherViewModel
import com.example.rapidweatherapp.utils.LocationHelper
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    single<WeatherApi> {
        get<Retrofit>().create(WeatherApi::class.java)
    }

    single<IWeatherRepository> { WeatherRepository(get()) }

    single { LocationHelper(get()) }

    viewModel { WeatherViewModel(get(), get()) }
}

