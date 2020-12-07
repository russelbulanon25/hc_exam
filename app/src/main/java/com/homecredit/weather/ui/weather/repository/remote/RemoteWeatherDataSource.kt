package com.homecredit.weather.ui.weather.repository.remote

import com.homecredit.weather.data.api.weather.GroupedWeatherDto
import com.homecredit.weather.data.api.weather.WeatherDto
import io.reactivex.rxjava3.core.Single

interface RemoteWeatherDataSource {

    fun getWeatherFromCities(cityIds: List<Int>): Single<GroupedWeatherDto>

    fun getWeatherFromCity(cityId: Int): Single<WeatherDto>
}