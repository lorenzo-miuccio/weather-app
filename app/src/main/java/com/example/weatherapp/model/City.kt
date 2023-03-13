package com.example.weatherapp.model

data class City(val name: String, val countryId: String) {

    val id: String
    get() = "$name, $countryId"

    override fun toString(): String {
        return id
    }
}