package com.homecredit.weather.ui.weather.repository.remote

import com.homecredit.weather.data.api.ApiHelper
import com.homecredit.weather.data.api.weather.WeatherApi
import com.homecredit.weather.data.api.weather.WeatherDto
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class RemoteWeatherDataSourceImpl(private val apiHelper: ApiHelper) : RemoteWeatherDataSource {

    override fun getWeatherFromCities(cityIds: List<Int>): Observable<WeatherDto> {
        return apiHelper
            .public(WeatherApi::class.java)
            .flatMap { it.getWeatherFromCities(cityIds) }
            .flatMapObservable { Observable.fromIterable(it.body()) }
    }

    override fun getWeatherFromCity(cityId: Int, appId: String): Single<WeatherDto> {
        return apiHelper
            .public(WeatherApi::class.java)
            .flatMap { it.getWeatherFromCity(cityId, appId) }
            .flatMap { Single.fromCallable { it.body() } }
    }
}