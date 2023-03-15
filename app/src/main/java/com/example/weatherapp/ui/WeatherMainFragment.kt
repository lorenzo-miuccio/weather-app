package com.example.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapp.databinding.FragmentWeatherMainBinding
import com.example.weatherapp.model.City
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherFetchState
import com.example.weatherapp.model.citiesList
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.text.Format
import java.text.SimpleDateFormat


class WeatherMainFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentWeatherMainBinding

    private val viewModel: WeatherViewModel by activityViewModels { WeatherViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWeatherMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()

        binding.weather.weatherImage.setOnClickListener {
            findNavController().navigate(R.id.action_weatherFragment_to_weatherDetailsFragment)
            viewModel.refreshWeather(true)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.weatherFetchState.collect { fetchState ->

                    binding.apply {
                        val progressIndicator = loading.progressIndicator
                        val weatherData = weather.weatherData
                        val imageError= error.imageError

                        when (fetchState) {
                            is WeatherFetchState.Loading -> {
                                progressIndicator.visibility = View.VISIBLE
                                weatherData.visibility = View.GONE
                                imageError.visibility = View.GONE
                            }
                            is WeatherFetchState.Success -> {
                                progressIndicator.visibility = View.GONE
                                weatherData.visibility = View.VISIBLE
                                imageError.visibility = View.GONE
                                bindWeatherDataViews(fetchState.weather, fetchState.secondsSinceLastFetch)
                            }
                            is WeatherFetchState.Error -> {
                                progressIndicator.visibility = View.GONE
                                weatherData.visibility = View.GONE
                                imageError.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }

        binding.refreshButton.setOnClickListener {
            viewModel.refreshWeather()
        }
    }

    private fun setupSpinner() {

        val adapter =
            ArrayAdapter(requireContext(), R.layout.spinner_item, citiesList)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    // Apply the adapter to the spinner
                    binding.weather.citySelector.adapter = adapter
                }

        val selectedCity = viewModel.getSelectedCity()

        for (index in 0 until adapter.count) {
            if (adapter.getItem(index) == selectedCity) {
                binding.weather.citySelector.setSelection(index, false)
                break
            }
        }

        binding.weather.citySelector.onItemSelectedListener = this
    }

    private fun bindWeatherDataViews(weather: Weather, secondsSinceLastFetch: Int) {
        binding.weather.apply {
            temperature.text = "${weather.temperature} °C"
            windSpeed.text = "${String.format("%.2f", weather.windSpeed)} km/h"
            val format = SimpleDateFormat("HH:mm")
            sunsetTime.text = format.format(weather.sunset.time)
            sunriseTime.text = format.format(weather.sunrise.time)
            humidity.text = "${weather.humidity} %"
            lastRemoteFetch.text = getString(R.string.last_update, secondsSinceLastFetch)
            Picasso.get().load(weather.iconPath).into(binding.weather.weatherImage)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        val selectedCity = parent.getItemAtPosition(position) as City
        viewModel.updateSelectedCity(selectedCity)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}
