package com.homecredit.weather

import com.homecredit.weather.BaseMockDataFactory.Companion.randomInt
import com.homecredit.weather.BaseMockDataFactory.Companion.randomString
import com.homecredit.weather.data.api.weather.GroupedWeatherForecastDto
import com.homecredit.weather.data.api.weather.WeatherForecastDto

class WeatherMockDataFactory {

    companion object {

        fun groupedWeatherDto(): GroupedWeatherForecastDto {
            return GroupedWeatherForecastDto(
                count = randomInt(),
                list = weatherDtos()
            )
        }

        fun weatherDto(): WeatherForecastDto {
            return WeatherForecastDto(
                name = randomString()
            )
        }

        fun weatherDtos(): ArrayList<WeatherForecastDto> {
            val weatherDtos = ArrayList<WeatherForecastDto>()

            for (i in 0 until randomInt()) {
                weatherDtos.add(weatherDto())
            }

            return weatherDtos
        }
    }
}