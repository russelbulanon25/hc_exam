package com.homecredit.weather.ui.weather.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homecredit.weather.ui.weather.repository.WeatherRepository
import com.homecredit.weather.ui.weather.repository.model.WeatherForecast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherListViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    var weatherForecasts = MutableLiveData<ArrayList<WeatherForecast>>(ArrayList())

    fun getWeatherFromCity() {
        weatherRepository
            .getWeatherForecastFromCities(listOf(1701668, 3067696, 1835848))
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<WeatherForecast>> {
                override fun onSuccess(t: List<WeatherForecast>) {
                    weatherForecasts.value = ArrayList(t)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                }
            })
    }
}