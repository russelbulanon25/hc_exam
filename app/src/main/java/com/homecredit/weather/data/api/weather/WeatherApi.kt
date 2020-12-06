package com.homecredit.weather.data.api.weather

import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    fun getWeatherFromCities(cityIds: List<Int>): Single<Response<List<WeatherDto>>>

    @GET("/data/2.5/weather")
    fun getWeatherFromCity(
        @Query("id") cityId: Int,
        @Query("appid") appId: String
    ): Single<Response<WeatherDto>>
}