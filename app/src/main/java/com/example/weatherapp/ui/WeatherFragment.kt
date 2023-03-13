package com.example.weatherapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherFetchState
import com.example.weatherapp.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat


class WeatherFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentWeatherBinding

    private val viewModel: WeatherViewModel by viewModels { WeatherViewModel.Factory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.cities_name,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.weather.citySelector.adapter = adapter
        }

        binding.weather.citySelector.onItemSelectedListener = this


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.weatherFetchState.collect { fetchState ->
                    when (fetchState) {
                        is WeatherFetchState.Loading -> {
                            binding.progressIndicator.visibility = View.VISIBLE
                            binding.weather.weatherData.visibility = View.GONE
                            binding.error.visibility = View.GONE
                        }
                        is WeatherFetchState.Success -> {
                            binding.progressIndicator.visibility = View.GONE
                            binding.weather.weatherData.visibility = View.VISIBLE
                            binding.error.visibility = View.GONE
                            bindWeatherDataViews(fetchState.weather)
                        }
                        is WeatherFetchState.Error -> {
                            binding.progressIndicator.visibility = View.GONE
                            binding.weather.weatherData.visibility = View.GONE
                            binding.error.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        binding.weather.refreshButton.setOnClickListener {
            viewModel.refreshWeather()
        }
    }

    private fun bindWeatherDataViews(weather: Weather) {
        binding.weather.apply {
            temperature.text = "${weather.temperature} °C"
            windSpeed.text = "${String.format("%.2f", weather.windSpeed)} km/h"
            val format = SimpleDateFormat("HH:mm")
            sunsetTime.text = format.format(weather.sunset.time)
            sunriseTime.text = format.format(weather.sunrise.time)
            humidity.text = "${weather.humidity} %"
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        val cityName = parent.getItemAtPosition(position) as String

        viewModel.updateSelectedCity(cityName)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}
