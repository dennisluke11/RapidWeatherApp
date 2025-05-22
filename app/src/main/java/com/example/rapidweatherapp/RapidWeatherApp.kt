package com.example.rapidweatherapp

import android.app.Application
import com.example.rapidweatherapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RapidWeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RapidWeatherApp)
            modules(appModule)
        }
    }
}