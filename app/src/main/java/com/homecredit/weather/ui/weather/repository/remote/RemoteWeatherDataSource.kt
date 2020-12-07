package com.homecredit.weather.ui.weather.repository.remote

import com.homecredit.weather.data.api.weather.GroupedWeatherForecastDto
import com.homecredit.weather.data.api.weather.WeatherForecastDto
import io.reactivex.rxjava3.core.Single

interface RemoteWeatherDataSource {

    fun getWeatherForecastFromCities(cityIds: List<Int>): Single<GroupedWeatherForecastDto>

    fun getWeatherForecastFromCity(cityId: Int): Single<WeatherForecastDto>
}