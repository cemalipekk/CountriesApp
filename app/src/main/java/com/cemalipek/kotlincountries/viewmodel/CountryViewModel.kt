package com.cemalipek.kotlincountries.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.cemalipek.kotlincountries.model.Country
import com.cemalipek.kotlincountries.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) : BaseViewModel(application) {
    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid : Int){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.getCountry(uuid)
            val country = dao.getCountry(uuid)
            countryLiveData.value = country
        }


    }
}