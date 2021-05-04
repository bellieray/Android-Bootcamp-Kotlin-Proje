package com.example.harcamatakipuygulamasi.service

import com.example.harcamatakipuygulamasi.model.Currency
import retrofit2.Call
import retrofit2.http.GET

interface CurrencyAPI {
    @GET("api/latest")
    fun getCurrency() : Call<Currency>
}