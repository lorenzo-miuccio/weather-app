package com.example.weatherapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherFetchState
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class WeatherDetailsFragment : Fragment() {

    private lateinit var binding: FragmentWeatherDetailsBinding

    private val viewModel: WeatherViewModel by activityViewModels { WeatherViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.weatherFetchState.collect { fetchState ->

                    binding.apply {
                        val progressIndicator = loading.progressIndicator
                        val weatherDetailsData = weatherDetailsData
                        val imageError= error.imageError

                        when (fetchState) {
                            is WeatherFetchState.Loading -> {
                                progressIndicator.visibility = View.VISIBLE
                                weatherDetailsData.visibility = View.GONE
                                imageError.visibility = View.GONE
                            }
                            is WeatherFetchState.Success -> {
                                progressIndicator.visibility = View.GONE
                                weatherDetailsData.visibility = View.VISIBLE
                                imageError.visibility = View.GONE
                                bindWeatherDataViews(fetchState.weather)
                            }
                            is WeatherFetchState.Error -> {
                                progressIndicator.visibility = View.GONE
                                weatherDetailsData.visibility = View.GONE
                                imageError.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun bindWeatherDataViews(weather: Weather) {
        binding.apply {
            tempMax.text = "${weather.tempMax} °C"
            tempMin.text = "${weather.tempMin} °C"
            weatherDescription.text = "${weather.description}"
            cityName.text = viewModel.getSelectedCity().id
            Picasso.get().load(weather.iconPath).into(weatherImageDetails)
        }
    }
}