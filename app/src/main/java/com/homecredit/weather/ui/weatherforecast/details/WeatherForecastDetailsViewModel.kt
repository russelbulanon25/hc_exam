package com.homecredit.weather.ui.weatherforecast.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homecredit.weather.ui.UiState
import com.homecredit.weather.ui.weatherforecast.repository.WeatherForecastRepository
import com.homecredit.weather.ui.weatherforecast.repository.model.WeatherForecast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class WeatherForecastDetailsViewModel(private val weatherForecastRepository: WeatherForecastRepository) :
    ViewModel() {

    private val weatherForecastMutableLiveData =
        MutableLiveData<WeatherForecast>(null)

    val weatherForecastLiveData: LiveData<WeatherForecast>
            by this::weatherForecastMutableLiveData

    private val errorMessageMutableLiveData = MutableLiveData("")

    val errorMessage: LiveData<String> by this::errorMessageMutableLiveData

    private val uiStateMutableLiveData = MutableLiveData(UiState.SUCCESS)

    val uiStateLiveData: LiveData<UiState> by this::uiStateMutableLiveData

    fun setWeatherForecastFromArgs(weatherForecast: WeatherForecast) {
        this.weatherForecastMutableLiveData.postValue(weatherForecast)
    }

    fun refreshData() {
        weatherForecastMutableLiveData.value?.let {
            weatherForecastRepository
                .getWeatherForecastFromCity(
                    it.locationId
                )
                .apply {
                    uiStateMutableLiveData.postValue(UiState.LOADING)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<WeatherForecast> {
                    override fun onSuccess(t: WeatherForecast) {
                        uiStateMutableLiveData.postValue(UiState.SUCCESS)

                        // retain current value of favorite
                        t.favorite = it.favorite

                        weatherForecastMutableLiveData.postValue(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        Timber.e(e)
                        uiStateMutableLiveData.postValue(UiState.FAILED)
                        e.message?.let {
                            errorMessageMutableLiveData.postValue(it)
                        }
                    }
                })
        }

    }

    fun toggleFavorite() {
        weatherForecastMutableLiveData.value?.let {
            it.favorite = !it.favorite

            weatherForecastMutableLiveData.postValue(it)
        }
    }
}