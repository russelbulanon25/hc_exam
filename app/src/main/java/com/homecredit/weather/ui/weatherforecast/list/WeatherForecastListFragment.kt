package com.homecredit.weather.ui.weatherforecast.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.homecredit.weather.databinding.FragmentWeatherForecastListBinding
import com.homecredit.weather.itemWeatherForecast
import com.homecredit.weather.ui.UiState
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherForecastListFragment : Fragment() {

    private var binding: FragmentWeatherForecastListBinding? = null

    private val readyOnlyBinding by this::binding

    private val weatherListViewModel: WeatherForecastListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherForecastListBinding.inflate(inflater, container, false)

        weatherListViewModel.getWeatherFromCity()

        weatherListViewModel.weatherForecastsLiveData.observe(
            viewLifecycleOwner,
            { weatherForecasts ->
                readyOnlyBinding?.epoxyRecyclerview?.withModels {
                    weatherForecasts.forEachIndexed { index, weather ->
                        itemWeatherForecast {
                            id(index)
                            data(weather)
                        }
                    }
                }
            })

        readyOnlyBinding?.swipeRefreshLayout?.setOnRefreshListener {
            weatherListViewModel.getWeatherFromCity()
        }

        weatherListViewModel.errorMessage.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty().not()) {
                activity?.let { activity ->
                    Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                }
            }
        })

        weatherListViewModel.uiStateLiveData.observe(viewLifecycleOwner, {
            it?.let {
                readyOnlyBinding?.swipeRefreshLayout?.isRefreshing = it == UiState.LOADING
            }
        })

        return readyOnlyBinding?.root
    }
}