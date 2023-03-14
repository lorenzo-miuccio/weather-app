package com.example.weatherapp.datasource.database

import android.content.Context
import androidx.room.*
import java.util.*

@Database(entities = [WeatherDBEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null
        fun getDatabase(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
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