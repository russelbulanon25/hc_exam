package com.homecredit.weather.data.api.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GroupedWeatherForecastDto(

    @Json(name = "cnt")
    val count: Int,

    val list: List<WeatherForecastDto>
)

@JsonClass(generateAdapter = true)
data class WeatherForecastDto(

    val main: WeatherMainDataDto,

    val weatherDto: WeatherDto,

    val name: String
)

@JsonClass(generateAdapter = true)
data class WeatherDto(

    val main: String
)

@JsonClass(generateAdapter = true)
data class WeatherMainDataDto(

    @Json(name = "temp")
    val temperature: Double
)