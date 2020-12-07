package com.homecredit.weather.ui.weatherforecast.repository.mapper

import com.homecredit.weather.data.api.weather.WeatherForecastDto
import com.homecredit.weather.data.api.weather.WeatherForecastStatusDto
import com.homecredit.weather.ui.weatherforecast.repository.model.WeatherForecast
import com.homecredit.weather.ui.weatherforecast.repository.model.WeatherForecast.Companion.HEX_COLOR_COLD
import com.homecredit.weather.ui.weatherforecast.repository.model.WeatherForecast.Companion.HEX_COLOR_FREEZING
import com.homecredit.weather.ui.weatherforecast.repository.model.WeatherForecast.Companion.HEX_COLOR_HOT
import com.homecredit.weather.ui.weatherforecast.repository.model.WeatherForecast.Companion.HEX_COLOR_WARM
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.ObservableTransformer

class DtoToWeatherForecastMapper : ObservableTransformer<WeatherForecastDto, WeatherForecast> {

    override fun apply(
        upstream: Observable<WeatherForecastDto>
    ): ObservableSource<WeatherForecast> {
        return upstream.flatMap {

            Observable.fromCallable {
                WeatherForecast(
                    currentTemperature = it.weatherForecastMainDto.currentTemperature,
                    backgroundHexColor =
                    backgroundHexColorFrom(it.weatherForecastMainDto.currentTemperature),
                    locationId = it.locationId,
                    locationName = it.locationName,
                    minTemperature = it.weatherForecastMainDto.minTemperature,
                    maxTemperature = it.weatherForecastMainDto.maxTemperature,
                    weatherStatus = weatherStatusFrom(it.weatherForecastStatusDto)
                )
            }
        }
    }

    private fun weatherStatusFrom(
        weatherForecastStatusDto: List<WeatherForecastStatusDto>?
    ): String {
        return if (weatherForecastStatusDto.isNullOrEmpty()) {
            ""
        } else {
            weatherForecastStatusDto[0].status
        }
    }

    private fun backgroundHexColorFrom(currentTemperature: Double): String {
        return when {
            currentTemperature <= 0 -> HEX_COLOR_FREEZING
            currentTemperature in 1.0..15.0 -> HEX_COLOR_COLD
            currentTemperature in 16.0..30.0 -> HEX_COLOR_WARM
            else -> HEX_COLOR_HOT
        }
    }
}