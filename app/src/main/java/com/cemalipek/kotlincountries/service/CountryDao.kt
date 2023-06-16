package com.cemalipek.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cemalipek.kotlincountries.model.Country

@Dao
interface CountryDao {

    //Data Acces Object /// Burada veri tabanımıza ulaşmak istediğimiz metodları yazacağız

    @Insert
    suspend fun insertAll(vararg countries: Country) : List<Long>

    /*
    Kotlin'de “suspend” kelimesi, bir fonksiyonun bir koşullu duraklatılmasını
    ve daha sonra tekrar devam etmesini sağlar. Bir fonksiyon “suspend” olarak
    işaretlendiğinde, bu fonksiyon çağrılırken, çağrının devam etmesi için bir
    koroutine içinde çalıştırılması gerekir.
     */
    //Insert -> INSERT INTO
    //suspend -> corotuine, pause & resume
    //vararg ->  multiple country objects
    //List<Long> -> primary keys



    @Query("SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()


}