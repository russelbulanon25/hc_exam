package com.homecredit.weather.ui.weather.repository.remote

import com.homecredit.weather.data.api.ApiHelper
import com.homecredit.weather.data.api.weather.GroupedWeatherDto
import com.homecredit.weather.data.api.weather.WeatherApi
import com.homecredit.weather.data.api.weather.WeatherDto
import io.reactivex.rxjava3.core.Single

class RemoteWeatherDataSourceImpl(private val apiHelper: ApiHelper) : RemoteWeatherDataSource {

    override fun getWeatherFromCities(
        cityIds: List<Int>
    ): Single<GroupedWeatherDto> {
        return apiHelper
            .public(WeatherApi::class.java)
            .flatMap {
                it.getWeatherFromCities(
                    cityIds = cityIds.joinToString(",")
                )
            }
            .flatMap { Single.fromCallable { it.body() } }
    }

    override fun getWeatherFromCity(cityId: Int): Single<WeatherDto> {
        return apiHelper
            .public(WeatherApi::class.java)
            .flatMap { it.getWeatherFromCity(cityId) }
            .flatMap { Single.fromCallable { it.body() } }
    }
}