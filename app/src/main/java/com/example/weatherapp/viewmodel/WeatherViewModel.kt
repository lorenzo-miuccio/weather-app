package com.example.weatherapp.viewmodel

import android.app.Application
import android.text.Editable.Factory
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weatherapp.datasource.WeatherApi
import com.example.weatherapp.model.City
import com.example.weatherapp.model.WeatherFetchState
import com.example.weatherapp.model.citiesList
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepo: WeatherRepository): ViewModel() {
    private val _selectedCity: MutableLiveData<City> = MutableLiveData(citiesList[0])
    val selectedCity: LiveData<City>
    get() = _selectedCity

    private val _weatherFetchState: MutableStateFlow<WeatherFetchState> = MutableStateFlow(WeatherFetchState.Loading)
    val weatherFetchState: StateFlow<WeatherFetchState>
    get() = _weatherFetchState

    init {
        refreshWeather()
    }

    fun updateSelectedCity(newCity: City) {
        _selectedCity.value = newCity
        refreshWeather()
    }

    fun refreshWeather() {
        viewModelScope.launch {
            try {
                _weatherFetchState.value = WeatherFetchState.Loading
                val res = weatherRepo.getWeatherByCityId(_selectedCity.value!!.id)

                _weatherFetchState.value = WeatherFetchState.Success(res)
            } catch (e: Exception) {
                _weatherFetchState.value = WeatherFetchState.Error(e)
            }
        }
    }

    companion object {
        val Factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return WeatherViewModel(WeatherRepository(WeatherApi.retrofitService)) as T
            }
        }
    }
}