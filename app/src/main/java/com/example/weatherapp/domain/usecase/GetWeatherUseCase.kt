package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.CityRepository
import com.example.weatherapp.domain.WeatherRepository
import com.example.weatherapp.domain.model.Weather

class GetWeatherUseCase(
    private val weatherRepo: WeatherRepository,
    private val cityRepo: CityRepository
) {

    suspend operator fun invoke(forceRemoteFetch: Boolean): Weather =
        weatherRepo.getWeatherByCityId(cityRepo.selectedCity.id, forceRemoteFetch)
}