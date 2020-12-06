package com.homecredit.weather

import com.homecredit.weather.BaseMockDataFactory.Companion.randomInt
import com.homecredit.weather.BaseMockDataFactory.Companion.randomString
import com.homecredit.weather.data.api.weather.GroupedWeatherDto
import com.homecredit.weather.data.api.weather.WeatherDto

class WeatherMockDataFactory {

    companion object {

        fun groupedWeatherDto(): GroupedWeatherDto {
            return GroupedWeatherDto(
                count = randomInt(),
                list = weatherDtos()
            )
        }

        fun weatherDto(): WeatherDto {
            return WeatherDto(
                name = randomString()
            )
        }

        fun weatherDtos(): ArrayList<WeatherDto> {
            val weatherDtos = ArrayList<WeatherDto>()

            for (i in 0 until randomInt()) {
                weatherDtos.add(weatherDto())
            }

            return weatherDtos
        }
    }
}