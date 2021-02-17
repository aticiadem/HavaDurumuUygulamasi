package com.adematici.weatherapp.model


import com.google.gson.annotations.SerializedName

data class Main(
        val humidity: Int,
        val pressure: Int,
        val temp: Double,
        val temp_max: Double,
        val temp_min: Double
)