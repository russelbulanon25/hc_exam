package com.homecredit.weather.ui.weather.repository.mapper

import com.homecredit.weather.data.api.weather.WeatherForecastDto
import com.homecredit.weather.ui.weather.repository.model.WeatherForecast
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.ObservableTransformer

class DtoToWeatherForecastMapper : ObservableTransformer<WeatherForecastDto, WeatherForecast> {

    override fun apply(upstream: Observable<WeatherForecastDto>): ObservableSource<WeatherForecast> {
        return upstream.flatMap {
            Observable.fromCallable {
                WeatherForecast(name = it.name)
            }
        }
    }
}