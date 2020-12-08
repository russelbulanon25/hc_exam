package com.homecredit.weather.ui.weatherforecast.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.homecredit.weather.databinding.FragmentWeatherForecastListBinding
import com.homecredit.weather.itemWeatherForecast
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherForecastListFragment : Fragment(), WeatherForecastListFragmentNavigator {

    private var _binding: FragmentWeatherForecastListBinding? = null

    private val binding get() = _binding!!

    private val viewModel: WeatherForecastListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherForecastListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigator = this

        viewModel.weatherForecastsLiveData.observe(
            viewLifecycleOwner,
            { weatherForecasts ->
                binding.epoxyRecyclerview?.withModels {
                    weatherForecasts.forEachIndexed { index, weather ->
                        itemWeatherForecast {
                            id(index)
                            data(weather)
                            onClickListener { _ ->
                                findNavController().navigate(
                                    WeatherForecastListFragmentDirections
                                        .toWeatherForecastDetailsFragment(
                                            weather
                                        )
                                )
                            }
                        }
                    }
                }
            })

        binding.swipeRefreshLayout?.setOnRefreshListener {
            viewModel.refreshData()
        }

        viewModel.getWeatherForecastFromCities()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onShowLoading() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    override fun onDismissLoading() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    override fun onShowErrorMessage(errorMessage: String) {
        if (errorMessage.isNullOrEmpty().not()) {
            Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}