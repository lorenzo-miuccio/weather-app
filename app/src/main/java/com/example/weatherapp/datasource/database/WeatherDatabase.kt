package com.example.weatherapp.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.*

@Database(entities = [WeatherDBEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}

private class Converters {
    @TypeConverter
    fun toCalendarDate(millisecondsSinceEpoch: Long): Calendar {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = millisecondsSinceEpoch
        return calendar
    }

    @TypeConverter
    fun dateToMillisecondsSinceEpoch(dateTime: Calendar): Long {
        return dateTime.timeInMillis
    }
}