package com.homecredit.weather.data.api.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GroupedWeatherDto(

    @Json(name = "cnt")
    val count: Int,

    val list: List<WeatherDto>
)

@JsonClass(generateAdapter = true)
data class WeatherDto(

    val name: String
)