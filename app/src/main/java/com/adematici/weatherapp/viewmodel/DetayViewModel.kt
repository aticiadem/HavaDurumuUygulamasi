package com.adematici.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adematici.weatherapp.model.WeatherModel
import com.adematici.weatherapp.service.WeatherAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DetayViewModel: ViewModel() {

    private val service = WeatherAPIService()
    private val compositeDisposable = CompositeDisposable()

    val errorMessage = MutableLiveData<Boolean>()
    val loadingMessage = MutableLiveData<Boolean>()
    val detaylar = MutableLiveData<WeatherModel>()

    fun getData(sehir: String){
        getDataFromAPI(sehir)
    }

    private fun getDataFromAPI(sehir: String){
        loadingMessage.value = false
        compositeDisposable.add(service.getData(sehir)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<WeatherModel>(){
                    override fun onSuccess(t: WeatherModel) {
                        detaylar.value = t
                        errorMessage.value = false
                        loadingMessage.value = false
                    }

                    override fun onError(e: Throwable) {
                        errorMessage.value = true
                        loadingMessage.value = false
                    }
                })
        )
    }

}