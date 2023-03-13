package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weatherapp.datasource.WeatherApi
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Objects

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repo = WeatherRepository(WeatherApi.retrofitService)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                Log.d("main", "start fetch")
                try {
                    val resp = repo.getWeatherByCityId("bologna")

                    Log.d(
                        "main",
                        "${resp.sunrise.get(Calendar.HOUR_OF_DAY)}:${resp.sunrise.get(Calendar.MINUTE)}"
                    )
                } catch (e: Exception) {
                    Log.d("main", e.toString())
                }
            }
        }
    }
}