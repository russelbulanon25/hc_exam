package com.homecredit.weather.ui.weather.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homecredit.weather.ui.UiState
import com.homecredit.weather.ui.weather.repository.WeatherRepository
import com.homecredit.weather.ui.weather.repository.model.WeatherForecast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class WeatherListViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    private val weatherForecastsMutableLiveData =
        MutableLiveData<ArrayList<WeatherForecast>>(ArrayList())

    val weatherForecastsLiveData: LiveData<ArrayList<WeatherForecast>>
            by this::weatherForecastsMutableLiveData

    private val errorMessageMutableLiveData = MutableLiveData("")

    val errorMessage: LiveData<String> by this::errorMessageMutableLiveData

    private val uiStateMutableLiveData = MutableLiveData(UiState.LOADING)

    val uiStateLiveData: LiveData<UiState> by this::uiStateMutableLiveData

    fun getWeatherFromCity() {
        weatherRepository
            .getWeatherForecastFromCities(listOf(1701668, 3067696, 1835848))
            .toList()
            .apply {
                uiStateMutableLiveData.postValue(UiState.LOADING)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<WeatherForecast>> {
                override fun onSuccess(t: List<WeatherForecast>) {
                    uiStateMutableLiveData.postValue(UiState.SUCCESS)
                    weatherForecastsMutableLiveData.postValue(ArrayList(t))
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