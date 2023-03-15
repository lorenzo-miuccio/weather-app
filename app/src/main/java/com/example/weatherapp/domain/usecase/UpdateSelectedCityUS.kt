package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.CityRepository
import com.example.weatherapp.domain.model.City

class UpdateSelectedCityUS(private val cityRepository: CityRepository) {
    operator fun invoke(city: City) {
        cityRepository.selectedCity = city
    }
}