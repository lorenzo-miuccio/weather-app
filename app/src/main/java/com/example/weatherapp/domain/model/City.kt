package com.example.weatherapp.domain.model

data class City(val name: String, val countryId: String) {

    val id: String
    get() = "$name, $countryId"

    override fun toString(): String {
        return name
    }
}

val cityList = listOf(
    City("Rome","IT"),
    City("Bologna", "IT"),
    City("Montreal", "CA"),
    City("New York", "US"),
)