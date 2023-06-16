package com.cemalipek.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cemalipek.kotlincountries.adapter.CountryAdapter
import com.cemalipek.kotlincountries.databinding.FragmentFeedBinding
import com.cemalipek.kotlincountries.model.Country
import com.cemalipek.kotlincountries.viewmodel.FeedViewModel


class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java) //FeedFragment ile FeedViewModel'i birbirine bağladık
        viewModel.refreshData()

        binding.countryList.layoutManager = LinearLayoutManager(context) // item_countryleri alt alta göstermemizi sağlaması için LinearLayout seçtik yanyana oluşturmak isteseydik gridLayout seçerdik
        binding.countryList.adapter = countryAdapter



        /*binding.buttonFragment.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
            Navigation.findNavController(it).navigate(action)
        }

         */
        binding.swipeRefreshLayout.setOnRefreshListener { //Bura Loading imgesinin işlevini yazdığımız yer
            binding.countryList.visibility = View.GONE
            binding.countryError.visibility = View.GONE
            binding.countryLoading.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer {countries ->

            countries?.let {
                binding.countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }

        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer {error ->
            error?.let {
                if (it) {
                    binding.countryError.visibility = View.VISIBLE
                } else{
                    binding.countryError.visibility = View.GONE
                }
            }
        })


        viewModel.countryLoading.observe(viewLifecycleOwner, Observer {loading ->
            loading?.let {
                if (it) {
                    binding.countryLoading.visibility = View.VISIBLE
                    binding.countryList.visibility = View.GONE
                    binding.countryError.visibility = View.GONE
                }else{
                    binding.countryLoading.visibility = View.GONE
                }
            }
        })
    }

    
}