package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.CityRepository
import com.example.weatherapp.domain.model.City

class GetSelectedCityUS(private val cityRepository: CityRepository) {
    operator fun invoke() = cityRepository.selectedCity
}