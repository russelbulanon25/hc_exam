package com.homecredit.weather.ui.weather.repository

import com.homecredit.weather.ui.weather.repository.model.WeatherForecast
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {

    fun getWeatherForecastFromCities(cityIds: List<Int>): Observable<WeatherForecast>

    fun getWeatherForecastFromCity(cityId: Int): Single<WeatherForecast>
}