package com.example.weatherapp.model

sealed class WeatherFetchState {
    class Success(val weather: Weather): WeatherFetchState()
    object Loading : WeatherFetchState()
    class Error(exception: Exception): WeatherFetchState()
}