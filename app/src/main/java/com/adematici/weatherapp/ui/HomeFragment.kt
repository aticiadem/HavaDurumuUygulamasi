package com.adematici.weatherapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adematici.weatherapp.databinding.FragmentHomeBinding
import com.adematici.weatherapp.model.WeatherModel
import com.adematici.weatherapp.service.WeatherAPI
import com.adematici.weatherapp.viewmodel.HomeViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var sehir: String
    private lateinit var viewModel: HomeViewModel

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

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.refreshData(sehir)

        observeLiveData()
    }

    @SuppressLint("SetTextI18n")
    private fun observeLiveData(){
        viewModel.veriler.observe(viewLifecycleOwner, Observer { gelenVeri ->
            gelenVeri?.let {
                binding.cardViewHome.visibility = View.VISIBLE
                binding.textViewError.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.INVISIBLE

                binding.textViewSehir.text = sehir.toUpperCase()
                binding.textViewHavaninDurumu.text = it.weather[0].description.toUpperCase()
                binding.textViewAnlikDerece.text = "Şu An "+it.main.temp.toInt().toString()+ " Derece"
                binding.textViewNem.text = "Nem "+it.main.humidity.toString()
                binding.textViewRuzgarHizi.text = "Ruzgar Hizi "+it.wind.speed.toString()
            }
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if(it){
                    binding.textViewError.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.cardViewHome.visibility = View.GONE
                } else {
                    binding.textViewError.visibility = View.GONE
                }
            }
        })
        viewModel.loadingMessage.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if(it){
                    binding.progressBar.visibility = View.VISIBLE
                    binding.textViewError.visibility = View.GONE
                    binding.cardViewHome.visibility = View.GONE
                }
                else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

}