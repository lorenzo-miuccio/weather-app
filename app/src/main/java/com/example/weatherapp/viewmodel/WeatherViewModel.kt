package com.example.weatherapp.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.weatherapp.MyApplication
import com.example.weatherapp.model.City
import com.example.weatherapp.model.WeatherFetchState
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepo: WeatherRepository) : ViewModel() {

    private val _weatherFetchState: MutableStateFlow<WeatherFetchState> =
        MutableStateFlow(WeatherFetchState.Loading)
    val weatherFetchState: StateFlow<WeatherFetchState>
        get() = _weatherFetchState

    init {
        refreshWeather(true)
    }

    fun updateSelectedCity(newCity: City) {
        weatherRepo.selectedCity = newCity
        refreshWeather(true)
    }

    fun refreshWeather(forceRemoteFetch: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _weatherFetchState.value = WeatherFetchState.Loading
                val res = weatherRepo.getWeather(forceRemoteFetch)
                _weatherFetchState.value = WeatherFetchState.Success(
                    weather = res,
                    secondsSinceLastFetch = (weatherRepo.millisecondsSinceLastFetch * 1000).toInt()
                )
            } catch (e: Exception) {
                _weatherFetchState.value = WeatherFetchState.Error(e)
            }
        }
    }

    fun getSelectedCity() = weatherRepo.selectedCity


    // Define ViewModel factory in a companion object
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                return WeatherViewModel((application as MyApplication).weatherRepository) as T
            }
        }
    }
}