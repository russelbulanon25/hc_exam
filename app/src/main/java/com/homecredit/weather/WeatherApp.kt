package com.homecredit.weather

import android.app.Application
import com.homecredit.weather.di.module.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WeatherApp)
            modules(listOf(networkModule))
        }
    }
}