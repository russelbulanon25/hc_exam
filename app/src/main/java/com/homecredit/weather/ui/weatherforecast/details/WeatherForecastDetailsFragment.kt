package com.homecredit.weather.ui.weatherforecast.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.homecredit.weather.R
import com.homecredit.weather.databinding.FragmentWeatherForecastDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherForecastDetailsFragment : Fragment(), WeatherForecastDetailsFragmentNavigator {

    private var _binding: FragmentWeatherForecastDetailsBinding? = null

    private val binding get() = _binding!!

    private val viewModel: WeatherForecastDetailsViewModel by viewModel()

    private val args: WeatherForecastDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWeatherForecastDetailsBinding.inflate(inflater, container, false)

        viewModel.setWeatherForecastFromArgs(args.weatherForecast)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigator = this

        viewModel.weatherForecastLiveData.observe(viewLifecycleOwner, {
            it?.let { weatherForecast ->
                activity?.let { activity ->

                    binding.tvCurrentTemperature.text = String.format(
                        activity.getString(R.string.current_temperature),
                        weatherForecast.currentTemperature
                    )

                    binding.ivFavorite.setBackgroundResource(
                        if (weatherForecast.favorite)
                            R.drawable.ic_favorite_black_24
                        else
                            R.drawable.ic_favorite_border_black_24
                    )

                    binding.tvLocationName.text = weatherForecast.locationName

                    binding.tvMaxMinTemp.text = String.format(
                        activity.getString(R.string.max_min_temperature),
                        weatherForecast.maxTemperature.toInt(),
                        weatherForecast.minTemperature.toInt()
                    )

                    binding.tvWeatherStatus.text = weatherForecast.weatherStatus
                }
            }
        })

        binding.ivFavorite.setOnClickListener {
            viewModel.toggleFavorite()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }
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