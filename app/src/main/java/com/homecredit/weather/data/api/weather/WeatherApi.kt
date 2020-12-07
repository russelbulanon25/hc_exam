package com.homecredit.weather.data.api.weather

import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/group")
    fun getWeatherForecastFromCities(
        @Query("id", encoded = true) cityIds: String
    ): Single<Response<GroupedWeatherForecastDto>>

    @GET("/data/2.5/weather")
    fun getWeatherForecastFromCity(
        @Query("id") cityId: Int
    ): Single<Response<WeatherForecastDto>>
}