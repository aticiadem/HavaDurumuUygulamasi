package com.adematici.weatherapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adematici.weatherapp.databinding.FragmentDetayBinding
import com.adematici.weatherapp.viewmodel.DetayViewModel

class DetayFragment : Fragment() {

    private lateinit var binding: FragmentDetayBinding
    private lateinit var sehir: String
    private lateinit var viewModel: DetayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetayBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sp = requireActivity().getSharedPreferences("SehirIsmi", Context.MODE_PRIVATE)
        sehir = sp.getString("sehirIsmi","Ankara").toString()

        viewModel = ViewModelProviders.of(this).get(DetayViewModel::class.java)
        viewModel.getData(sehir)

        observeLiveData()
    }

    @SuppressLint("SetTextI18n")
    private fun observeLiveData(){
        viewModel.detaylar.observe(viewLifecycleOwner,Observer{ veriler ->
            veriler?.let {
                binding.cardViewHome.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.textViewError.visibility = View.GONE

                binding.textViewAnlikDerece.text = "Şu An ("+it.main.temp.toInt().toString()+ ") Derece"
                binding.textViewSehir.text = sehir.toUpperCase()
                binding.textViewHavaninDurumu.text = "Hava: "+it.weather[0].description.toUpperCase()
                binding.textViewRuzgarHizi.text = "Rüzgar Hızı: "+it.wind.speed.toInt().toString()+" km/s"
                binding.textViewNem.text = "Nem: %"+it.main.humidity.toString()
            }
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            error?.let { 
                if (it){
                    binding.textViewError.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.cardViewHome.visibility = View.GONE
                } else {
                    binding.textViewError.visibility = View.GONE
                }
            }
        })
        viewModel.loadingMessage.observe(viewLifecycleOwner,Observer{ loading ->
            loading?.let { 
                if (it){
                    binding.textViewError.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.cardViewHome.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

}