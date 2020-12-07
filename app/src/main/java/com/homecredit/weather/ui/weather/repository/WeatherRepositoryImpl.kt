package com.homecredit.weather.ui.weather.repository

import com.homecredit.weather.ui.weather.repository.mapper.DtoToWeatherForecastMapper
import com.homecredit.weather.ui.weather.repository.model.WeatherForecast
import com.homecredit.weather.ui.weather.repository.remote.RemoteWeatherDataSource
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

// we could need the LocalWeatherDataSource dependency here in future if we want to support caching
class WeatherRepositoryImpl(
    private val remoteWeatherDataSource: RemoteWeatherDataSource
) : WeatherRepository {

    override fun getWeatherForecastFromCities(cityIds: List<Int>): Observable<WeatherForecast> {
        return remoteWeatherDataSource
            .getWeatherForecastFromCities(cityIds)
            .flatMapObservable { Observable.fromIterable(it.weatherForecastDtoList) }
            .compose(DtoToWeatherForecastMapper())
    }

    override fun getWeatherForecastFromCity(cityId: Int): Single<WeatherForecast> {
        return remoteWeatherDataSource
            .getWeatherForecastFromCity(cityId)
            .toObservable()
            .compose(DtoToWeatherForecastMapper())
            .firstOrError()
    }
}