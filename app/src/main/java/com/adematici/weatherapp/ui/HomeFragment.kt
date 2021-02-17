package com.adematici.weatherapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adematici.weatherapp.databinding.FragmentHomeBinding
import com.adematici.weatherapp.model.WeatherModel
import com.adematici.weatherapp.service.WeatherAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var sehir: String
    private val BASE_URL = "http://api.openweathermap.org/"
    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            sehir = HomeFragmentArgs.fromBundle(it).sehirIsmi
        }

        compositeDisposable = CompositeDisposable()
        loadData(sehir)
    }

    private fun loadData(sehir: String){
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(WeatherAPI::class.java)

        compositeDisposable.add(retrofit.getCurrentWeather(sehir)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse))
    }

    @SuppressLint("SetTextI18n")
    private fun handleResponse(veriListesi: WeatherModel){
        veriListesi.let {
            binding.textView.text = "${it.weather[0].description.toUpperCase()} \n" +
                    "${it.main.tempMax} \n" +
                    "${it.main.humidity} \n" +
                    "${it.wind.speed} \n"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}