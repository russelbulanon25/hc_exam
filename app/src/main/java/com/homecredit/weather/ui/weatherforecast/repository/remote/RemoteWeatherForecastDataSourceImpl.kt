package com.homecredit.weather.ui.weatherforecast.repository.remote

import com.homecredit.weather.data.api.ApiHelper
import com.homecredit.weather.data.api.weather.GroupedWeatherForecastDto
import com.homecredit.weather.data.api.weather.WeatherApi
import com.homecredit.weather.data.api.weather.WeatherForecastDto
import io.reactivex.rxjava3.core.Single

class RemoteWeatherForecastDataSourceImpl(private val apiHelper: ApiHelper) : RemoteWeatherForecastDataSource {

    override fun getWeatherForecastFromCities(
        cityIds: List<Int>
    ): Single<GroupedWeatherForecastDto> {
        return apiHelper
            .public(WeatherApi::class.java)
            .flatMap {
                it.getWeatherForecastFromCities(
                    cityIds = cityIds.joinToString(",")
                )
            }
            .flatMap { Single.fromCallable { it.body() } }
    }

    override fun getWeatherForecastFromCity(cityId: Int): Single<WeatherForecastDto> {
        return apiHelper
            .public(WeatherApi::class.java)
            .flatMap { it.getWeatherForecastFromCity(cityId) }
            .flatMap { Single.fromCallable { it.body() } }
    }
}