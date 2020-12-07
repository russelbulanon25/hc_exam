package com.homecredit.weather.di

import com.homecredit.weather.ui.weather.list.WeatherListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        WeatherListViewModel(get())
    }
}