package com.homecredit.weather.ui.weatherforecast.repository

import com.homecredit.weather.ui.weatherforecast.repository.remote.RemoteWeatherForecastDataSource
import com.homecredit.weather.ui.weatherforecast.repository.remote.RemoteWeatherForecastDataSourceImpl
import org.koin.dsl.module

val weatherForecastRepositoryModule = module {

    factory<RemoteWeatherForecastDataSource> { (RemoteWeatherForecastDataSourceImpl(get())) }

    factory<WeatherForecastRepository> { (WeatherForecastRepositoryImpl(get(), get())) }
}