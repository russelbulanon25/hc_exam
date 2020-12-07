package com.homecredit.weather.ui.weather.repository

import com.homecredit.weather.ui.weather.repository.mapper.DtoToWeatherMapper
import com.homecredit.weather.ui.weather.repository.model.Weather
import com.homecredit.weather.ui.weather.repository.remote.RemoteWeatherDataSource
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

// we could need the LocalWeatherDataSource dependency here in future if we want to support caching
class WeatherRepositoryImpl(
    private val remoteWeatherDataSource: RemoteWeatherDataSource
) : WeatherRepository {

    override fun getWeatherForecastFromCities(cityIds: List<Int>): Observable<Weather> {
        return remoteWeatherDataSource
            .getWeatherForecastFromCities(cityIds)
            .flatMapObservable { Observable.fromIterable(it.list) }
            .compose(DtoToWeatherMapper())
    }

    override fun getWeatherForecastFromCity(cityId: Int): Single<Weather> {
        return remoteWeatherDataSource
            .getWeatherForecastFromCity(cityId)
            .toObservable()
            .compose(DtoToWeatherMapper())
            .firstOrError()
    }
}