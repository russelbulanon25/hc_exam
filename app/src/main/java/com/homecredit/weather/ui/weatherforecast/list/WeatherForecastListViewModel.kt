package com.homecredit.weather.ui.weatherforecast.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homecredit.weather.Constants.Companion.LOCATION_ID_MANILA
import com.homecredit.weather.Constants.Companion.LOCATION_ID_PRAGUE
import com.homecredit.weather.Constants.Companion.LOCATION_ID_SEOUL
import com.homecredit.weather.ui.weatherforecast.repository.WeatherForecastRepository
import com.homecredit.weather.ui.weatherforecast.repository.model.WeatherForecast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class WeatherForecastListViewModel(private val weatherForecastRepository: WeatherForecastRepository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val weatherForecastsMutableLiveData =
        MutableLiveData<ArrayList<WeatherForecast>>(ArrayList())

    val weatherForecastsLiveData: LiveData<ArrayList<WeatherForecast>>
            by this::weatherForecastsMutableLiveData

    lateinit var navigator: WeatherForecastListFragmentNavigator

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
                navigator.onShowLoading()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<WeatherForecast>> {
                override fun onSuccess(t: List<WeatherForecast>) {
                    navigator.onDismissLoading()

                    weatherForecastsMutableLiveData.value = (ArrayList(t))
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}