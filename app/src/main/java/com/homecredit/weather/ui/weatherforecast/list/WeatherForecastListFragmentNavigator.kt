package com.homecredit.weather.ui.weatherforecast.list

interface WeatherForecastListFragmentNavigator {

    fun onShowLoading()

    fun onDismissLoading()
    
    fun onShowErrorMessage(errorMessage: String)
}