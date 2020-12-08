package com.homecredit.weather

import android.app.Application
import com.homecredit.weather.di.module.networkModule
import com.homecredit.weather.di.module.repositoryModule
import com.homecredit.weather.di.viewModelModule
import com.homecredit.weather.util.utilModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@WeatherApp)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    utilModule,
                    viewModelModule
                )
            )
        }
    }
}