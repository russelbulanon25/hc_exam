package com.homecredit.weather.ui.weather.repository

import com.homecredit.weather.ui.weather.repository.remote.RemoteWeatherDataSource
import com.homecredit.weather.ui.weather.repository.remote.RemoteWeatherDataSourceImpl
import org.koin.dsl.module

val weatherRepositoryModule = module {

    factory<RemoteWeatherDataSource> { (RemoteWeatherDataSourceImpl(get())) }

    factory<WeatherRepository> { (WeatherRepositoryImpl(get())) }
}