package com.homecredit.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.homecredit.weather.databinding.ActivityMainBinding
import com.homecredit.weather.ui.weatherforecast.list.WeatherListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}