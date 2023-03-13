package com.example.weatherapp.repository

import com.example.weatherapp.datasource.WeatherApiService
import com.example.weatherapp.datasource.apiresponse.WeatherApiResp
import com.example.weatherapp.model.Weather
import java.util.*

class WeatherRepository(private val api: WeatherApiService) {
    suspend fun getWeatherByCityId(cityId: String): Weather =
        api.getWeatherByCityId(cityId).toEntity()
}

private fun WeatherApiResp.toEntity(): Weather {
    val iconPath = "http://openweathermap.org/img/wn/" + weather[0].iconPath + "@2x.png"

    sunTimes.sunrise.add(Calendar.SECOND, timezoneInSeconds)
    sunTimes.sunset.add(Calendar.SECOND, timezoneInSeconds)

    return Weather(
        iconPath = iconPath,
        temperature = main.temp,
        humidity = main.humidity,
        windSpeed = wind.speed * 3.6,
        sunrise = sunTimes.sunrise,
        sunset = sunTimes.sunset
    )
}