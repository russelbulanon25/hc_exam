package com.homecredit.weather.ui.weatherforecast.repository

import com.homecredit.weather.ui.weatherforecast.repository.mapper.DtoToWeatherForecastMapper
import com.homecredit.weather.ui.weatherforecast.repository.model.WeatherForecast
import com.homecredit.weather.ui.weatherforecast.repository.remote.RemoteWeatherForecastDataSource
import com.homecredit.weather.util.connection.ConnectionUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

// we could need the LocalWeatherDataSource dependency here in future if we want to support caching
class WeatherForecastRepositoryImpl(
    private val connectionUtil: ConnectionUtil,
    private val remoteWeatherForecastDataSource: RemoteWeatherForecastDataSource
) : WeatherForecastRepository {

    override fun getWeatherForecastFromCities(cityIds: List<Int>): Observable<WeatherForecast> {
        return connectionUtil
            .isConnected()
            .flatMapObservable { _ ->
                remoteWeatherForecastDataSource
                    .getWeatherForecastFromCities(cityIds)
                    .flatMapObservable { Observable.fromIterable(it.weatherForecastDtoList) }
                    .compose(DtoToWeatherForecastMapper())
            }
    }

    override fun getWeatherForecastFromCity(cityId: Int): Single<WeatherForecast> {
        return connectionUtil
            .isConnected()
            .flatMap {
                remoteWeatherForecastDataSource
                    .getWeatherForecastFromCity(cityId)
                    .toObservable()
                    .compose(DtoToWeatherForecastMapper())
                    .firstOrError()
            }
    }
}