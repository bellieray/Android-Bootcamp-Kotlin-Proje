package com.example.harcamatakipuygulamasi.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.harcamatakipuygulamasi.model.Harcama

@Database(entities = [Harcama::class],version = 9)
abstract  class HarcamaDatabase :  RoomDatabase() {

    abstract fun harcamaDao(): HarcamaDao


}