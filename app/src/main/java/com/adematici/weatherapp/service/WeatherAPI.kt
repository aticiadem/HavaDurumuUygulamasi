package com.adematici.weatherapp.service

import com.adematici.weatherapp.model.WeatherModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("data/2.5/weather")
    fun getCurrentWeather(
            @Query("q") location: String,
            @Query("lang") language: String = "tr",
            @Query("units") units: String = "metric",
            @Query("appid") appid: String = "" // your api key
    ) : Single<WeatherModel>

}