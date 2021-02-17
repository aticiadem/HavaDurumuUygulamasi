package com.adematici.weatherapp.model


import com.google.gson.annotations.SerializedName

data class WeatherModel(
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)