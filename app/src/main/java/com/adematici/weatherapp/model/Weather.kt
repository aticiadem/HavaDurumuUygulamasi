package com.adematici.weatherapp.model


import com.google.gson.annotations.SerializedName

data class Weather(
    val description: String,
    val main: String
)