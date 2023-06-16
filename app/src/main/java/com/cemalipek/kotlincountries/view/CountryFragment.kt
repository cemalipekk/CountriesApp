package com.cemalipek.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.cemalipek.kotlincountries.R
import com.cemalipek.kotlincountries.databinding.FragmentCountryBinding
import com.cemalipek.kotlincountries.databinding.FragmentFeedBinding
import com.cemalipek.kotlincountries.util.downloadFromUrl
import com.cemalipek.kotlincountries.util.placeholderProgressBar
import com.cemalipek.kotlincountries.viewmodel.CountryViewModel


class CountryFragment : Fragment() {
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CountryViewModel

    private var countryUuid = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)

        observeLiveData()

    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {
                context?.let {
                    binding.countryImage.downloadFromUrl(country.imageUrl, placeholderProgressBar(it))
                }
                binding.countryName.text = country.countryName
                binding.countryCapital.text = country.countryCapital
                binding.countryRegion.text = country.countryRegion
                binding.countryCurrency.text = country.countryCurrency
                binding.countryLanguage.text = country.countryLanguage



            }
        })

    }

}


