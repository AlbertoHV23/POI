package com.poi.camppus.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.R
import com.poi.camppus.models.Encriptacion
import com.poi.camppus.models.LLAVES
import com.poi.camppus.models.tbl_Mensajes
import java.security.Key

class MensajesAdapter(val context: Context, var LISTA:List<tbl_Mensajes>): RecyclerView.Adapter<MensajesAdapter.Holder>(){
    val firebase  = FirebaseFirestore.getInstance();
    class Holder(val view: View):RecyclerView.ViewHolder(view) {
        private lateinit var auth: FirebaseAuth
        fun render(mensajes: tbl_Mensajes) {
            auth = FirebaseAuth.getInstance()
            val myMessageLayout:ConstraintLayout = view.findViewById(R.id.myMessageLayout)
            val otherMessage:ConstraintLayout = view.findViewById(R.id.otherMessageLayout)

            val mio:TextView = view.findViewById(R.id.myMessageTextView)
            val other:TextView = view.findViewById(R.id.othersMessageTextView)
            println(mensajes.message)
            
            if(mensajes.encriptado == true){
                var descifarMensaje = Encriptacion.descifar(mensajes.message,LLAVES.MESSAGE.toString())
                println(descifarMensaje)
                mensajes.message = descifarMensaje
            }

            if(mensajes.from.equals(auth.currentUser.email)){
                myMessageLayout.visibility = View.VISIBLE
                otherMessage.visibility = View.GONE
                mio.text = mensajes.message

            }
            else{
                otherMessage.visibility = View.VISIBLE
                myMessageLayout.visibility = View.GONE
                other.text = mensajes.message
            }








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