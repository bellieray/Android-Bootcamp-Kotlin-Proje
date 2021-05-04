package com.example.harcamatakipuygulamasi.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.harcamatakipuygulamasi.R
import com.example.harcamatakipuygulamasi.service.HarcamaDatabase
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_harcama_detayi.*

class HarcamaDetayiFragment : Fragment() {

    //HARCAMA ID ALINMASI
    val args : HarcamaDetayiFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_harcama_detayi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //DATABASE
        val db: HarcamaDatabase = Room.databaseBuilder(requireContext(),
            HarcamaDatabase::class.java,"harcamalar")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()


        val harcamaId = args.HarcamaId

        val kur = args.kur
        val kurİsareti = args.kurIsareti
        val harcama =db.harcamaDao().harcamaGetir(harcamaId)

        harcamaDetayiTurText.text = harcama.harcamaTuru
        harcamaDetayiFiyatText.text = "${harcama.harcamaFiyati!! / kur} ${kurİsareti}"
        var kategori = harcama.harcamaKategorisi

            //HARCAMA KATEGORİSİNE GÖRE RESİM EKLEME
        when(kategori){
           "fatura" -> Glide.with(view).load(R.drawable.fatura).apply(RequestOptions().override(350,350)).into(harcamaDetayImageView)
            "diger" -> Glide.with(view).load(R.drawable.diger).apply(RequestOptions().override(350,350)).into(harcamaDetayImageView)
            "kira" -> Glide.with(view).load(R.drawable.kira).apply(RequestOptions().override(350,350)).into(harcamaDetayImageView)
        }

        harcamaDetayBackButton.setOnClickListener {
            val action = HarcamaDetayiFragmentDirections.actionHarcamaDetayiFragmentToAnaEkranFragment()
                Navigation.findNavController(it).navigate(action)

        }

        //HARCAMA SİLME
        harcamaDetaySilButton.setOnClickListener {
            val alert = AlertDialog.Builder(activity)
            alert.setTitle("Harcama Silme")
            alert.setMessage("Emin misiniz ? ")
            alert.setPositiveButton("Evet",DialogInterface.OnClickListener { dialog, which ->
                db.harcamaDao().seciliOgeyiSil(harcamaId)
                val snackbar = Snackbar.make(it,"Seçili öge silindi", Snackbar.LENGTH_LONG)
                snackbar.setAction("Tamam",View.OnClickListener {
                    snackbar.dismiss()
                })
                snackbar.setActionTextColor(Color.RED)
                snackbar.show()
                val actionToFirst = HarcamaDetayiFragmentDirections.actionHarcamaDetayiFragmentToAnaEkranFragment()
                Navigation.findNavController(it).navigate(actionToFirst)
            })
            alert.setNegativeButton("Hayır",DialogInterface.OnClickListener { dialog, which ->
                val snackbar = Snackbar.make(it,"Silme işlemi iptal edildi", Snackbar.LENGTH_LONG)
                snackbar.setAction("Tamam",View.OnClickListener {
                    snackbar.dismiss()
                })
                snackbar.setActionTextColor(Color.RED)
                snackbar.show()
            })
            alert.show()



        }
    }

}