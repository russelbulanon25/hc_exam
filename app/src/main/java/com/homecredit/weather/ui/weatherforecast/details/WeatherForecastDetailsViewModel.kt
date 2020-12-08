package com.homecredit.weather.ui.weatherforecast.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homecredit.weather.ui.weatherforecast.repository.WeatherForecastRepository
import com.homecredit.weather.ui.weatherforecast.repository.model.WeatherForecast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class WeatherForecastDetailsViewModel(private val weatherForecastRepository: WeatherForecastRepository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val weatherForecastMutableLiveData =
        MutableLiveData<WeatherForecast>(null)

    val weatherForecastLiveData: LiveData<WeatherForecast>
            by this::weatherForecastMutableLiveData

    lateinit var navigator: WeatherForecastListFragmentNavigator

    fun setWeatherForecastFromArgs(weatherForecast: WeatherForecast) {
        this.weatherForecastMutableLiveData.value = weatherForecast
    }

    fun refreshData() {
        weatherForecastMutableLiveData.value?.let {
            weatherForecastRepository
                .getWeatherForecastFromCity(
                    it.locationId
                )
                .apply {
                    navigator.onShowLoading()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<WeatherForecast> {
                    override fun onSuccess(t: WeatherForecast) {
                        navigator.onDismissLoading()

                        // update values of current selected weather forecast data
                        it.backgroundHexColor = t.backgroundHexColor
                        it.currentTemperature = t.currentTemperature
                        it.maxTemperature = t.maxTemperature
                        it.minTemperature = t.minTemperature
                        it.weatherStatus = t.weatherStatus

                        weatherForecastMutableLiveData.value = it
                    }

                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onError(e: Throwable) {
                        Timber.e(e)
                        navigator.onDismissLoading()

                        e.message?.let { errorMessage ->
                            navigator.onShowErrorMessage(errorMessage)
                        }
                    }
                })
        }

    }

    fun toggleFavorite() {
        weatherForecastMutableLiveData.value?.let {
            it.favorite = !it.favorite
            weatherForecastMutableLiveData.value = it
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}