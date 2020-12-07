package com.homecredit.weather.ui.weather.repository

import com.homecredit.weather.ui.weather.repository.model.Weather
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {

    fun getWeatherForecastFromCities(cityIds: List<Int>): Observable<Weather>

    fun getWeatherForecastFromCity(cityId: Int): Single<Weather>
}