package com.example.harcamatakipuygulamasi.view

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import com.example.harcamatakipuygulamasi.R
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_changing_name.*

class ChangingNameFragment : Fragment() {
    lateinit var isim :String
    lateinit var  sharedPreferences: SharedPreferences
    lateinit var  editor : SharedPreferences.Editor


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_changing_name, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ANA EKRANA GİRİLEN İSMİ YOLLAMA
        sharedPreferences = requireContext().getSharedPreferences("giris",MODE_PRIVATE)
        editor = sharedPreferences.edit()

        //TOOLBAR
        nameToolbar.setTitle("İsim Değiştirme Ekranı")

            //CİNSİYET ALMA
            radioGroup.setOnCheckedChangeListener { group, checkedId ->

                if(checkedId == R.id.radioErkek ){
                    isim = editTextAd.text.toString() + " Bey"
                }
                if(checkedId == R.id.radioKadın){
                    isim = editTextAd.text.toString() + " Hanım"
                }
                if(checkedId == R.id.radioBosluk){
                    isim = editTextAd.text.toString()
                }



            }

            //İSİM KAYDETME İŞLEMLERİ
        buttonNameKaydet.setOnClickListener {

            if (!editTextAd.text.isNullOrEmpty() && (radioErkek.isChecked || radioKadın.isChecked || radioBosluk.isChecked)) {
                editor.putString("isim",isim)
                editor.commit()
                Toast.makeText(activity,"Hoşgeldiniz Kullanıcı Bilgileriniz Alındı",Toast.LENGTH_LONG).show()
                buttonNameKaydet.isClickable = true
                val action = ChangingNameFragmentDirections.actionChangingNameFragmentToAnaEkranFragment(isim.toString())
                Navigation.findNavController(it).navigate(action)

            }else {
                val snackbar = Snackbar.make(it,"Lütfen boş alan bırakmayınız ", Snackbar.LENGTH_LONG)
                snackbar.setAction("Tamam",View.OnClickListener {
                    snackbar.dismiss()
                })
                snackbar.setActionTextColor(Color.RED)
                snackbar.show()

            }

        }


    }

}