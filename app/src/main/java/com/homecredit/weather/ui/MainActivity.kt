package com.homecredit.weather.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.homecredit.weather.databinding.ActivityMainBinding
import com.homecredit.weather.itemWeatherForecast
import com.homecredit.weather.ui.weather.list.WeatherListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val weatherListViewModel: WeatherListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weatherListViewModel.getWeatherFromCity()

        weatherListViewModel.weatherForecastsLiveData.observe(this, { weatherForecasts ->
            binding.epoxyRecyclerview.withModels {
                weatherForecasts.forEachIndexed { index, weather ->
                    itemWeatherForecast {
                        id(index)
                        data(weather)
                    }
                }
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            weatherListViewModel.getWeatherFromCity()
        }

        weatherListViewModel.errorMessage.observe(this, {
            if (it.isNullOrEmpty().not()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        weatherListViewModel.uiStateLiveData.observe(this, {
            it?.let {
                binding.swipeRefreshLayout.isRefreshing = it == UiState.LOADING
            }
        })
    }
}