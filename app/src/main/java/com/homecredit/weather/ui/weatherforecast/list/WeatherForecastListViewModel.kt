package com.homecredit.weather.ui.weatherforecast.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homecredit.weather.Constants.Companion.LOCATION_ID_MANILA
import com.homecredit.weather.Constants.Companion.LOCATION_ID_PRAGUE
import com.homecredit.weather.Constants.Companion.LOCATION_ID_SEOUL
import com.homecredit.weather.ui.UiState
import com.homecredit.weather.ui.weatherforecast.repository.WeatherForecastRepository
import com.homecredit.weather.ui.weatherforecast.repository.model.WeatherForecast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class WeatherForecastListViewModel(private val weatherForecastRepository: WeatherForecastRepository) :
    ViewModel() {

    private val weatherForecastsMutableLiveData =
        MutableLiveData<ArrayList<WeatherForecast>>(ArrayList())

    val weatherForecastsLiveData: LiveData<ArrayList<WeatherForecast>>
            by this::weatherForecastsMutableLiveData

    private val errorMessageMutableLiveData = MutableLiveData("")

    val errorMessage: LiveData<String> by this::errorMessageMutableLiveData

    private val uiStateMutableLiveData = MutableLiveData(UiState.LOADING)

    val uiStateLiveData: LiveData<UiState> by this::uiStateMutableLiveData

    fun getWeatherForecastFromCities() {
        if (weatherForecastsMutableLiveData.value.isNullOrEmpty()) {
            refreshData()
        }
    }

    fun refreshData() {
        weatherForecastRepository
            .getWeatherForecastFromCities(
                listOf(
                    LOCATION_ID_MANILA,
                    LOCATION_ID_PRAGUE,
                    LOCATION_ID_SEOUL
                )
            )
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