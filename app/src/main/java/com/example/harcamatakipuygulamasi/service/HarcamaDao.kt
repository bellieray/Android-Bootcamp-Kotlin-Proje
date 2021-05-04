package com.example.harcamatakipuygulamasi.service

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.harcamatakipuygulamasi.model.Harcama

@Dao
interface HarcamaDao {

    @Insert
    fun harcamaEkle(harcama : Harcama)

    @Query("Delete from harcama where harcamaTuruId = :id")
    fun seciliOgeyiSil(id : Int)

    @Query("Select * from harcama")
    fun tumListeyiAl() : List<Harcama>

    @Query("Select * from harcama where harcamaTuruId = :id")
    fun harcamaGetir(id : Int) : Harcama






}