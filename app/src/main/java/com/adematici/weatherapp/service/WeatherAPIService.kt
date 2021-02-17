package com.adematici.weatherapp.service

import com.adematici.weatherapp.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherAPIService {

    private val BASE_URL = "http://api.openweathermap.org/"
    private val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(WeatherAPI::class.java)

    fun getData(sehir: String) : Single<WeatherModel>{
        return api.getCurrentWeather(sehir)
    }

}

