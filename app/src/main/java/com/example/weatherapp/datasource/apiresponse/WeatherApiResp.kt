package com.example.weatherapp.datasource.apiresponse

import android.util.Log
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson
import java.util.*

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class Date

data class WeatherApiResp(
    val weather: List<WeatherDescriptionEntity>,
    val main: MainWeatherEntity,
    val wind: WindEntity,
    @Json(name = "sys")
    val sunTimes: SunTimesEntity,
    @Json(name = "timezone")
    val timezoneInSeconds: Int
) {
    data class WeatherDescriptionEntity(@Json(name = "icon") val iconPath: String)
    data class WindEntity(val speed: Double)
    data class SunTimesEntity(@Date val sunrise: Calendar, @Date val sunset: Calendar)
    data class MainWeatherEntity(val temp: Double, val humidity: Int)
}


internal class DateAdapter {
    @ToJson
    fun toJson(@Date dateTime: Calendar): Long {
        return dateTime.timeInMillis
    }

    @FromJson
    @Date
    fun fromJson(secondsSinceEpoch: Long): Calendar {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = secondsSinceEpoch * 1000
        return calendar
    }
}