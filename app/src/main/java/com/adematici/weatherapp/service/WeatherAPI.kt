package com.adematici.weatherapp.service

import com.adematici.weatherapp.model.WeatherModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/weather")
    fun getCurrentWeather(
            @Query("q") location: String,
            @Query("lang") language: String = "tr",
            @Query("appid") appid: String = ""
    ) : Observable<WeatherModel>

}