package com.example.weatherapp.ui.state

import com.example.weatherapp.domain.model.Weather

sealed class WeatherFetchState {
    class Success(val weather: Weather): WeatherFetchState()
    object Loading : WeatherFetchState()
    class Error(exception: Exception): WeatherFetchState()
}