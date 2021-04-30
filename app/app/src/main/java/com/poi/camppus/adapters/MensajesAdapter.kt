package com.poi.camppus.adapters

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alonsodelcid.multichat.models.tbl_Chat
import com.google.firebase.auth.FirebaseAuth
import com.poi.camppus.MensajesActivity
import com.poi.camppus.R
import com.poi.camppus.models.tbl_Mensajes

class MensajesAdapter(val context: Context, var LISTA:List<tbl_Mensajes>): RecyclerView.Adapter<MensajesAdapter.Holder>(){
    class Holder(val view: View):RecyclerView.ViewHolder(view) {
        private lateinit var auth: FirebaseAuth
        fun render(mensajes: tbl_Mensajes) {
            auth = FirebaseAuth.getInstance()





            var txt: TextView = view?.findViewById(R.id.mensaje_usuario)
            txt.text= mensajes.from

            var tvMensaje: TextView = view?.findViewById(R.id.tvMensaje)
            tvMensaje.text= mensajes.message





        }






    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MensajesAdapter.Holder(layoutInflater.inflate(R.layout.item_lista_mensajes, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.render(LISTA[position])
    }

    override fun getItemCount(): Int {
        return LISTA.size
    }
}