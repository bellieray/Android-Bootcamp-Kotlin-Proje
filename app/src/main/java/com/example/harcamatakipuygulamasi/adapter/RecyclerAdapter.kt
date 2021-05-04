package com.example.harcamatakipuygulamasi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.harcamatakipuygulamasi.R
import com.example.harcamatakipuygulamasi.model.Harcama
import com.example.harcamatakipuygulamasi.view.AnaEkranFragmentDirections
import kotlinx.android.synthetic.main.fragment_splash_screen.view.*
import kotlinx.android.synthetic.main.recycler_row.view.*

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.CardViewHolder>{
    var harcamaListesi : ArrayList<Harcama>
    var context : Context
     var kur : Int = 0
    var kurİsareti : String? = null

     constructor(harcamaListesi : ArrayList<Harcama>,context: Context) {
         this.harcamaListesi = harcamaListesi
         this.context = context

     }

    class CardViewHolder(itemView: View ) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)
        return  CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.itemView.textHarcamaTuruRecycler.text = harcamaListesi.get(position).harcamaTuru
        holder.itemView.textHarcamaFiyatiRecycler.text =
            (harcamaListesi.get(position).harcamaFiyati.toString().toInt() / kur!!).toString() + kurİsareti
        holder.itemView.imageRecycler.setImageResource(context.getResources().getIdentifier(harcamaListesi.get(position).harcamaKategorisi, "drawable", context.getPackageName())
        )
        holder.itemView.setOnClickListener {
        val action = AnaEkranFragmentDirections.actionAnaEkranFragmentToHarcamaDetayiFragment(harcamaListesi.get(position).harcamaTuruId,kur, kurİsareti.toString())
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
      return  harcamaListesi.size
    }
    var list =  ArrayList<Harcama>()
    fun submitList(harcamaListesi: ArrayList<Harcama>){
        list.clear()
        list.addAll(harcamaListesi)
        this.notifyDataSetChanged()
    }

    fun kurAl(kur : Int, kurİsareti: String){
        this.kur = kur
        this.kurİsareti = kurİsareti
        this.notifyDataSetChanged()
    }
}