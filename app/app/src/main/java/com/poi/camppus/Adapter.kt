package com.poi.camppus

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alonsodelcid.multichat.models.tbl_Chat

class Adapter (val context: Context, var LISTA:List<tbl_Chat>): RecyclerView.Adapter<Adapter.Holder>(){
    class Holder(val view: View):RecyclerView.ViewHolder(view) {
        fun render(superHero: tbl_Chat) {
            var txt: TextView = view?.findViewById(R.id.id_texto)
            txt.text= superHero.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.item_lista_chats,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.render(LISTA[position])
    }

    override fun getItemCount(): Int {
        return LISTA.size
    }


}