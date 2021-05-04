package com.example.harcamatakipuygulamasi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Harcama(

    @ColumnInfo(name = "harcamaKategorisi")
    var harcamaKategorisi : String?,

    @ColumnInfo(name = "harcamaTuru")
    var harcamaTuru : String?,

    @ColumnInfo(name =  "harcamaFiyati")
    var harcamaFiyati : Int?,


) {
    @PrimaryKey(autoGenerate = true)
    var harcamaTuruId : Int = 0
}