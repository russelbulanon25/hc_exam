package com.homecredit.weather

import com.homecredit.weather.BaseMockDataFactory.Companion.randomInt
import com.homecredit.weather.BaseMockDataFactory.Companion.randomString
import com.homecredit.weather.data.api.weather.GroupedWeatherForecastDto
import com.homecredit.weather.data.api.weather.WeatherForecastDto
import com.homecredit.weather.data.api.weather.WeatherForecastMainDto
import com.homecredit.weather.data.api.weather.WeatherForecastStatusDto

class WeatherForecastMockDataFactory {

    companion object {

        fun groupedWeatherDto(): GroupedWeatherForecastDto {
            return GroupedWeatherForecastDto(
                count = randomInt(),
                weatherForecastDtoList = weatherForecastDtoList()
            )
        }

        private fun weatherForecastDtoList(): ArrayList<WeatherForecastDto> {
            val weatherDtos = ArrayList<WeatherForecastDto>()

            for (i in 0 until randomInt()) {
                weatherDtos.add(weatherForecastDto())
            }

            return weatherDtos
        }

        fun weatherForecastDto(
            currentTemperature: Double = randomInt().toDouble(),
            weatherForecastStatusDto: List<WeatherForecastStatusDto>? = weatherForecastStatusDtoList()
        ): WeatherForecastDto {
            return WeatherForecastDto(
                locationId = randomInt(),
                locationName = randomString(),
                weatherForecastMainDto = weatherForecastMainDto(currentTemperature),
                weatherForecastStatusDto = weatherForecastStatusDto
            )
        }

        private fun weatherForecastMainDto(currentTemperature: Double): WeatherForecastMainDto {
            return WeatherForecastMainDto(
                currentTemperature = currentTemperature,
                maxTemperature = randomInt().toDouble(),
                minTemperature = randomInt().toDouble()
            )
        }

        private fun weatherForecastStatusDtoList(): List<WeatherForecastStatusDto> {
            val dtoList = ArrayList<WeatherForecastStatusDto>()

            for (i in 0 until randomInt()) {
                dtoList.add(weatherForecastStatusDto())
            }

            return dtoList
        }

        private fun weatherForecastStatusDto(): WeatherForecastStatusDto {
            return WeatherForecastStatusDto(
                status = randomString()
            )
        }
    }
}