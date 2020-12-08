package com.homecredit.weather.ui.weatherforecast.details

interface WeatherForecastDetailsFragmentNavigator {

    fun onShowLoading()

    fun onDismissLoading()
    
    fun onShowErrorMessage(errorMessage: String)
}