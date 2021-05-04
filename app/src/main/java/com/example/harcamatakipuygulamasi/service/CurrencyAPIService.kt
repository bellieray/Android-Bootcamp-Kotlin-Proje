package com.example.harcamatakipuygulamasi.service

import com.example.harcamatakipuygulamasi.model.Currency
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyAPIService {

        private  val BASE_URL = "https://api.ratesapi.io/"
        private val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyAPI::class.java)

        fun getData() : Call<Currency>{
            return api.getCurrency()
        }





}