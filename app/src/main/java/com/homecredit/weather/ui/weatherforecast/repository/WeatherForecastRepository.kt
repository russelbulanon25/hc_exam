package com.homecredit.weather.ui.weatherforecast.repository

import com.homecredit.weather.ui.weatherforecast.repository.model.WeatherForecast
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface WeatherForecastRepository {

    fun getWeatherForecastFromCities(cityIds: List<Int>): Observable<WeatherForecast>

    fun getWeatherForecastFromCity(cityId: Int): Single<WeatherForecast>
}