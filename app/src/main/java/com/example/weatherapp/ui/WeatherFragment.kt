package com.example.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weatherapp.MyApplication
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.model.City
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherFetchState
import com.example.weatherapp.model.citiesList
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


class WeatherFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentWeatherBinding

    private val viewModel: WeatherViewModel by viewModels {
        val myApplication = requireActivity().application as MyApplication
        WeatherViewModel.Factory(myApplication.weatherRepository)
    }

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

        setupSpinner()

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

        binding.weather.citySelector.onItemSelectedListener = this

        val selectedCity = viewModel.getSelectedCity()

        for (index in 0 until adapter.count) {
            if (adapter.getItem(index) == selectedCity) {
                binding.weather.citySelector.setSelection(index)
                break
            }
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
