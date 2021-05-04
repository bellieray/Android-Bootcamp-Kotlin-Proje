package com.example.harcamatakipuygulamasi.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Currency(
   @SerializedName("base")
   val base : String?,
   @SerializedName("rates")
   val rate : HashMap<String, Float>?,
   @SerializedName("date")
   val date :String
){




 }