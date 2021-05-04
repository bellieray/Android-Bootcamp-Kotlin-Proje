package com.example.harcamatakipuygulamasi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.harcamatakipuygulamasi.R
import com.example.harcamatakipuygulamasi.model.Currency
import com.example.harcamatakipuygulamasi.service.CurrencyAPI
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}