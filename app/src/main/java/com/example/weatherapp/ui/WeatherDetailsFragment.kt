package com.example.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapp.model.WeatherFetchState
import com.example.weatherapp.ui.util.PageContentUtil
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class WeatherDetailsFragment : Fragment(), PageContentUtil {

    private lateinit var binding: FragmentWeatherDetailsBinding

    private val viewModel: WeatherViewModel by activityViewModels { WeatherViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBack()
        }
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

        binding.backButton.setOnClickListener {
            onBack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.weatherFetchState.collect { fetchState ->

                    binding.apply {
                        val progressIndicatorView = loading.progressIndicator
                        val weatherDetailsDataView = weatherDetailsData
                        val imageErrorView = error.imageError

                        setPageContent(
                            progressIndicatorView,
                            weatherDetailsDataView,
                            imageErrorView,
                            fetchState,
                            ::bindWeatherDataViews
                        )
                    }
                }
            }
        }
    }

    private fun onBack() {
        findNavController().popBackStack()
        viewModel.refreshWeather()
    }

    private fun bindWeatherDataViews(fetchDataState: WeatherFetchState.Success) {
        val weather = fetchDataState.weather
        binding.apply {
            tempMax.text = "${weather.tempMax} °C"
            tempMin.text = "${weather.tempMin} °C"
            weatherDescription.text = "${weather.description}"
            cityName.text = viewModel.getSelectedCity().id
            Picasso.get().load(weather.iconPath).into(weatherImageDetails)
        }
    }
}