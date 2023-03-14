package com.example.weatherapp.viewmodel

import androidx.lifecycle.*
import com.example.weatherapp.model.City
import com.example.weatherapp.model.WeatherFetchState
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepo: WeatherRepository): ViewModel() {

    private val _weatherFetchState: MutableStateFlow<WeatherFetchState> = MutableStateFlow(WeatherFetchState.Loading)
    val weatherFetchState: StateFlow<WeatherFetchState>
    get() = _weatherFetchState

    init {
        refreshWeather()
    }

    fun updateSelectedCity(newCity: City) {
        weatherRepo.setSelectedCity(newCity)
        refreshWeather()
    }

    fun refreshWeather() {
        viewModelScope.launch {
            try {
                _weatherFetchState.value = WeatherFetchState.Loading
                val res = weatherRepo.getWeather()
                _weatherFetchState.value = WeatherFetchState.Success(res)
            } catch (e: Exception) {
                _weatherFetchState.value = WeatherFetchState.Error(e)
            }
        }
    }
    fun getSelectedCity() = weatherRepo.getSelectedCity()

    class Factory(private val weatherRepo: WeatherRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return WeatherViewModel(weatherRepo) as T
        }
    }
}