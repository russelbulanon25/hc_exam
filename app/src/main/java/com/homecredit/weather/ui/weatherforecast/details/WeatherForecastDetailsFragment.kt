package com.homecredit.weather.ui.weatherforecast.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.homecredit.weather.databinding.FragmentWeatherForecastDetailsBinding

class WeatherForecastDetailsFragment : Fragment() {

    private var binding: FragmentWeatherForecastDetailsBinding? = null

    private val readyOnlyBinding by this::binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherForecastDetailsBinding.inflate(inflater, container, false)

        return readyOnlyBinding?.root
    }
}