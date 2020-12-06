package com.homecredit.weather.ui.weather.repository.remote

import com.homecredit.weather.data.api.weather.WeatherDto
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface RemoteWeatherDataSource {

    fun getWeatherFromCities(cityIds: List<Int>): Observable<WeatherDto>

    fun getWeatherFromCity(cityId: Int, appId: String): Single<WeatherDto>
}