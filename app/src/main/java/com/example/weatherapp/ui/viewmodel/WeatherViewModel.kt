package com.example.weatherapp.ui.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.weatherapp.MyApplication
import com.example.weatherapp.domain.model.City
import com.example.weatherapp.domain.usecase.GetSelectedCityUS
import com.example.weatherapp.domain.usecase.GetWeatherUseCase
import com.example.weatherapp.domain.usecase.UpdateSelectedCityUS
import com.example.weatherapp.ui.state.WeatherFetchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherUS: GetWeatherUseCase,
    private val getSelectedCityUS: GetSelectedCityUS,
    private val updateSelectedCityUS: UpdateSelectedCityUS
) : ViewModel() {

    private val _weatherFetchState: MutableStateFlow<WeatherFetchState> =
        MutableStateFlow(WeatherFetchState.Loading)
    val weatherFetchState: StateFlow<WeatherFetchState>
        get() = _weatherFetchState

    init {
        refreshWeather(true)
    }

    fun updateSelectedCity(newCity: City) {
        updateSelectedCityUS(newCity)
        refreshWeather(true)
    }

    fun refreshWeather(forceRemoteFetch: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _weatherFetchState.value = WeatherFetchState.Loading
                val res = getWeatherUS(forceRemoteFetch)
                _weatherFetchState.value = WeatherFetchState.Success(res)
            } catch (e: Exception) {
                _weatherFetchState.value = WeatherFetchState.Error(e)
            }
        }
    }

    fun getSelectedCity() = getSelectedCityUS()

    // Define ViewModel factory in a companion object
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY]) as MyApplication
                return WeatherViewModel(
                    GetWeatherUseCase(application.weatherRepository, application.cityRepository),
                    GetSelectedCityUS(application.cityRepository),
                    UpdateSelectedCityUS(application.cityRepository)
                ) as T
            }
        }
    }
}