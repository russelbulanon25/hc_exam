package com.homecredit.weather.ui.weather.repository.mapper

import com.homecredit.weather.data.api.weather.WeatherDto
import com.homecredit.weather.ui.weather.repository.model.Weather
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.ObservableTransformer

class DtoToWeatherMapper : ObservableTransformer<WeatherDto, Weather> {

    override fun apply(upstream: Observable<WeatherDto>): ObservableSource<Weather> {
        return upstream.flatMap {
            Observable.fromCallable {
                Weather(name = it.name)
            }
        }
    }
}