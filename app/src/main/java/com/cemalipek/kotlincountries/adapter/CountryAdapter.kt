package com.cemalipek.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.cemalipek.kotlincountries.databinding.FragmentCountryBinding
import com.cemalipek.kotlincountries.databinding.ItemCounrtyBinding
import com.cemalipek.kotlincountries.model.Country
import com.cemalipek.kotlincountries.util.downloadFromUrl
import com.cemalipek.kotlincountries.util.placeholderProgressBar
import com.cemalipek.kotlincountries.view.FeedFragmentDirections

class CountryAdapter(val countryList: ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(val binding: ItemCounrtyBinding ) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCounrtyBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.binding.name.text = countryList[position].countryName
        holder.binding.region.text = countryList[position].countryRegion

        holder.itemView.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.binding.imageView.downloadFromUrl(countryList[position].imageUrl, placeholderProgressBar(holder.itemView.context))
    }

    fun updateCountryList(newCountryList: List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}