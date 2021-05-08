package com.example.harcamatakipuygulamasi.view

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.harcamatakipuygulamasi.R
import com.example.harcamatakipuygulamasi.adapter.RecyclerAdapter
import com.example.harcamatakipuygulamasi.model.Currency
import com.example.harcamatakipuygulamasi.model.Harcama
import com.example.harcamatakipuygulamasi.model.MoneyType
import com.example.harcamatakipuygulamasi.service.CurrencyAPIService
import com.example.harcamatakipuygulamasi.service.HarcamaDatabase
import kotlinx.android.synthetic.main.fragment_ana_ekran.*
import kotlinx.android.synthetic.main.fragment_ana_ekran.view.*
import retrofit2.Call
import retrofit2.Response

class AnaEkranFragment : Fragment() {

    var harcamaListesi = ArrayList<Harcama>()
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var preferences: SharedPreferences

    companion object {
        var tlDolar: Int = 0
        var tl: Int = 0
        var tlSterlin: Int = 0
        lateinit var db : HarcamaDatabase
    }


    val toplam = MutableLiveData<MoneyType>()

    //onCreateBaslangıç
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (checkConnection(requireContext())) {
            Toast.makeText(activity, "Internet bağlı", Toast.LENGTH_LONG).show()
        } else {
            loadData()

          val toast = Toast.makeText(activity, "İnternet bağlı değil\nSon kullanıcı verileri alındı", Toast.LENGTH_SHORT)
            toast.view!!.setBackgroundColor(Color.parseColor("#FF5722"))
            toast.show()

        }

        //apiden verilerin alınması
        val call = CurrencyAPIService().getData()
        call.enqueue(object : retrofit2.Callback<Currency> {
            override fun onResponse(call: Call<Currency>, response: Response<Currency>) {
                val liste = response.body()
                val map = liste!!.rate
                var dolar = map!!.get("USD")
                var sterlin = map.get("GBP")
                tl = map.get("TRY")!!.toInt()
                tlDolar = (tl / dolar!!).toInt()
                tlSterlin = (tl / sterlin!!).toInt()
                saveData()
            }


            override fun onFailure(call: Call<Currency>, t: Throwable) {
                Log.e("hata", t.localizedMessage)
            }

        })


    }

    //onCreateBitiş

    //internet bağlantısı sorgulama
    fun checkConnection(context: Context): Boolean {
        val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ana_ekran, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toplam.observe(viewLifecycleOwner, ::toplamLive)

        preferences = requireContext().getSharedPreferences("giris", MODE_PRIVATE)
        view.isimTextGiris.text = preferences.getString("isim", "Adınız : ")
  

       db = Room.databaseBuilder(requireContext(), HarcamaDatabase::class.java, "harcamalar")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

        //recyclerViewAlma
        harcamaListesi = db.harcamaDao().tumListeyiAl() as ArrayList<Harcama>
        recyclerAdapter = RecyclerAdapter(harcamaListesi, view.context)
        recyclerAdapter.kurAl(1, "TL")
        harcamaToplamListe(harcamaListesi, 1, "TL")
        recyclerView.adapter = recyclerAdapter

        //dolar
        buttonDolarAnaEkran.setOnClickListener {
            recyclerAdapter.submitList(harcamaListesi)
            recyclerAdapter.kurAl(tlDolar, "$")
            harcamaToplamListe(harcamaListesi, tlDolar, "$")
        }

        //tl
        buttonTLAnaEkran.setOnClickListener {
            recyclerAdapter.submitList(harcamaListesi)
            harcamaToplamListe(harcamaListesi, 1, "TL")
            recyclerAdapter.kurAl(1, "TL")

        }
        //euro
        buttonEuroAnaEkran.setOnClickListener {
            recyclerAdapter.submitList(harcamaListesi)
            harcamaToplamListe(harcamaListesi, tl, "€")
            recyclerAdapter.kurAl(tl, "€")

        }

        //sterlin
        buttonSterlinAnaEKran.setOnClickListener {
            recyclerAdapter.submitList(harcamaListesi)
            harcamaToplamListe(harcamaListesi, tlSterlin, "£")
            recyclerAdapter.kurAl(tlSterlin, "£")

        }

        //isim degistirme
        isimTextGiris.setOnClickListener {
            val action = AnaEkranFragmentDirections.actionAnaEkranFragmentToChangingNameFragment()
            Navigation.findNavController(it).navigate(action)
        }

        //yeni harcama ekranı alanına geçiş
        fabButton.setOnClickListener {
            val actionToHarcama = AnaEkranFragmentDirections.actionAnaEkranFragmentToHarcamaEklemeFragment()
            Navigation.findNavController(it).navigate(actionToHarcama)

        }

    }

    private fun toplamLive(i: MoneyType) {
        i.let {
            toplamHarcamaTextGiris.text = it.degisken
        }
    }

    private fun updateToplam(deger: MoneyType) {
        toplam.value = deger
    }

    private fun harcamaToplamListe(harcamaListesi: ArrayList<Harcama>, kur: Int, kurIsareti: String) {
        var toplamDeger = 0
        for (i in harcamaListesi) {
            toplamDeger += i.harcamaFiyati!!.toInt()
        }
        if (kur != 0)
            updateToplam(MoneyType(kur, toplamDeger / kur, kurIsareti))
    }

    private fun saveData() {
        val preferences: SharedPreferences = requireContext().getSharedPreferences("kurlar", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preferences.edit()
        with(editor) {
            putInt("dolarKuru", tlDolar)
            putInt("euroKuru", tl)
            putInt("sterlinKuru", tlSterlin)
            commit()
        }
    }

    private fun loadData() {
        val preferencesLoad: SharedPreferences = requireContext().getSharedPreferences("kurlar", MODE_PRIVATE)
        var yeniDolarKuru: Int = preferencesLoad.getInt("dolarKuru", 0)
        val yeniEuroKuru: Int = preferencesLoad.getInt("euroKuru", 0)
        val yeniSterlinKuru: Int = preferencesLoad.getInt("sterlinKuru", 0)
        tlDolar = yeniDolarKuru
        tl = yeniEuroKuru
        tlSterlin = yeniSterlinKuru
    }


}