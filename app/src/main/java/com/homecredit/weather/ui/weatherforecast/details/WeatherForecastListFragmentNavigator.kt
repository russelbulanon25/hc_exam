package com.homecredit.weather.ui.weatherforecast.details

interface WeatherForecastListFragmentNavigator {

    fun onShowLoading()

    fun onDismissLoading()
    
    fun onShowErrorMessage(errorMessage: String)
}