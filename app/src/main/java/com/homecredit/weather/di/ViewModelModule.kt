package com.homecredit.weather.di

import com.homecredit.weather.ui.weatherforecast.details.WeatherForecastDetailsViewModel
import com.homecredit.weather.ui.weatherforecast.list.WeatherForecastListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        WeatherForecastDetailsViewModel(get())
    }

    viewModel {
        WeatherForecastListViewModel(get())
    }
}