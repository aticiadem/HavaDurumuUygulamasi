package com.adematici.weatherapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.adematici.weatherapp.databinding.FragmentAyarlarBinding

class AyarlarFragment : Fragment() {

    private lateinit var binding: FragmentAyarlarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAyarlarBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonOnayla.setOnClickListener {
            if(binding.editTextSehir.text.isNotEmpty()){
                val sehir = binding.editTextSehir.text.toString()

                val sp = requireActivity().getSharedPreferences("SehirIsmi",Context.MODE_PRIVATE)
                val editor = sp.edit()
                editor.putString("sehirIsmi",sehir)
                editor.apply()

                val action = AyarlarFragmentDirections.actionAyarlarFragmentToHomeFragment()
                Navigation.findNavController(it).navigate(action)
            } else {
                Toast.makeText(activity,"Lütfen Şehir Giriniz.",Toast.LENGTH_SHORT).show()
            }
        }
    }

}