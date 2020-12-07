package com.homecredit.weather.data.api.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GroupedWeatherForecastDto(

    @Json(name = "cnt")
    val count: Int,

    @Json(name = "list")
    val weatherForecastDtoList: List<WeatherForecastDto>
)

@JsonClass(generateAdapter = true)
data class WeatherForecastDto(

    @Json(name = "id")
    val locationId: Int,

    @Json(name = "name")
    val locationName: String,

    @Json(name = "main")
    val weatherForecastMainDto: WeatherForecastMainDto,

    @Json(name = "weather")
    val weatherForecastStatusDto: List<WeatherForecastStatusDto>?
)

@JsonClass(generateAdapter = true)
data class WeatherForecastStatusDto(

    @Json(name = "main")
    val status: String
)

@JsonClass(generateAdapter = true)
data class WeatherForecastMainDto(

    @Json(name = "temp")
    val currentTemperature: Double,

    @Json(name = "temp_max")
    val maxTemperature: Double,

    @Json(name = "temp_min")
    val minTemperature: Double
)