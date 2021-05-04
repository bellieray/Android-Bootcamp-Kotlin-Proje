package com.example.harcamatakipuygulamasi.model

data class MoneyType(
        var kur : Int,
        var deger : Int,
        var sembol : String

) {
    val degisken : String get() = deger.toString() + sembol
}