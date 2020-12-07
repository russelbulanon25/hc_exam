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
import com.homecredit.weather.ui.UiState
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherForecastListFragment : Fragment() {

    private var _binding: FragmentWeatherForecastListBinding? = null

    private val binding get() = _binding!!

    private val viewModel: WeatherForecastListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherForecastListBinding.inflate(inflater, container, false)

        viewModel.getWeatherForecastFromCities()

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

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty().not()) {
                activity?.let { activity ->
                    Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.uiStateLiveData.observe(viewLifecycleOwner, {
            it?.let {
                binding.swipeRefreshLayout?.isRefreshing = it == UiState.LOADING
            }
        })

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}