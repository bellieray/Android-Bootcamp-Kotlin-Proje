package com.example.harcamatakipuygulamasi.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.navigation.Navigation
import androidx.room.Room
import com.example.harcamatakipuygulamasi.R
import com.example.harcamatakipuygulamasi.model.Currency
import com.example.harcamatakipuygulamasi.model.Harcama
import com.example.harcamatakipuygulamasi.service.CurrencyAPIService
import com.example.harcamatakipuygulamasi.service.HarcamaDatabase
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_harcama_ekleme.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.math.roundToInt
import kotlin.properties.Delegates

class HarcamaEklemeFragment : Fragment() {
   lateinit  var harcamaKategorisi : String
   var harcamaFiyati by Delegates.notNull<Int>()
    var  tlDolar =  AnaEkranFragment.tlDolar
    var tl = AnaEkranFragment.tl
    var tlSterlin = AnaEkranFragment.tlSterlin


    override fun onCreateView(  inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_harcama_ekleme, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        harcamaEklemeToolbar.setTitle("Harcama Ekle")

        radioGrupHarcamaÇeşitleri.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radioFatura -> harcamaKategorisi = radioFatura.text.toString()
                R.id.radioKira ->   harcamaKategorisi = radioKira.text.toString()
                R.id.radioDiger -> harcamaKategorisi = radioDiger.text.toString()
            }

        }
            editTextHarcamaFiyati.text = null
        radioGrupDövizKurlari.setOnCheckedChangeListener { group, checkedId ->

                when (checkedId) {
                    R.id.radioTL ->  try {
                        harcamaFiyati =  editTextHarcamaFiyati.text.toString().toInt()
                    } catch (e : Exception){
                        e.printStackTrace()
                    }
                    R.id.radioDolar -> try {
                        harcamaFiyati = editTextHarcamaFiyati.text.toString().toInt() * (tlDolar)
                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                    R.id.radioEuro-> try {
                        harcamaFiyati = editTextHarcamaFiyati.text.toString().toInt() * (tl)
                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                    R.id.radioSterlin -> try {
                        harcamaFiyati = editTextHarcamaFiyati.text.toString().toInt() * (tlSterlin)
                    }catch (e :Exception){
                        e.printStackTrace()
                    }
                }


        }


        buttonHarcamaEkle.setOnClickListener {
          val harcamaTuru = editTextAciklama.text.toString()
            val alert = AlertDialog.Builder(activity)
            alert.setTitle("Harcama Ekleme")
            alert.setMessage("Emin misiniz?")

                if(harcamaTuru.isNotEmpty() && !editTextHarcamaFiyati.text.isNullOrEmpty() && harcamaKategorisi.isNotEmpty() && radioGrupDövizKurlari.isNotEmpty()){
                    alert.setPositiveButton("Evet",DialogInterface.OnClickListener { dialog, which ->
                        val harcama  = Harcama(harcamaKategorisi,harcamaTuru, harcamaFiyati )
                       AnaEkranFragment.db.harcamaDao().harcamaEkle(harcama)
                        val snackbar = Snackbar.make(it,"Öge Eklendi",Snackbar.LENGTH_LONG)
                        snackbar.setAction("Tamam",View.OnClickListener {
                            snackbar.dismiss()
                        })
                        snackbar.setActionTextColor(Color.RED)
                        snackbar.show()
                        val action = HarcamaEklemeFragmentDirections.actionHarcamaEklemeFragmentToAnaEkranFragment()
                        Navigation.findNavController(it).navigate(action)
                    })
                    alert.setNegativeButton("Hayır",DialogInterface.OnClickListener { dialog, which ->
                        val snackbar = Snackbar.make(it,"Secilen öge eklenmedi",Snackbar.LENGTH_LONG)
                        snackbar.setAction("Tamam",View.OnClickListener {
                            snackbar.dismiss()
                        })
                        snackbar.setActionTextColor(Color.RED)
                        snackbar.show()
                        editTextAciklama.text!!.clear()
                        editTextHarcamaFiyati.text!!.clear()

                    })
                    alert.show()
                }
                else {
                    val snackbar = Snackbar.make(it,"Lütfen boş alan bırakmayınız ",Snackbar.LENGTH_LONG)
                    snackbar.setAction("Tamam",View.OnClickListener {
                        snackbar.dismiss()
                    })
                    snackbar.setActionTextColor(Color.RED)
                    snackbar.show()
                }

        }

    }

}