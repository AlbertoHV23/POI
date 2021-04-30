package com.poi.camppus.adapters

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.alonsodelcid.multichat.models.tbl_Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ServerValue
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.MensajesActivity
import com.poi.camppus.R
import com.poi.camppus.activities.MainNavigationActivity
import com.poi.camppus.activities.SendmessagesActivity
import com.poi.camppus.models.ReferenciasFirebase
import com.poi.camppus.models.tbl_Mensajes

class ListaChatAdapter (val context: Context, var LISTA:List<tbl_Chat>): RecyclerView.Adapter<ListaChatAdapter.Holder>(){
    private lateinit var auth: FirebaseAuth
    val firebase  = FirebaseFirestore.getInstance();
    inner class Holder(val view: View):RecyclerView.ViewHolder(view), View.OnClickListener{
        lateinit var  email:String
        lateinit var id:String
        fun render(superHero: tbl_Chat) {
            if (superHero != null){

                var txt: TextView = view?.findViewById(R.id.id_texto)
                var men: TextView = view?.findViewById(R.id.txt_mensajeItem)
                var otherUser = ""
                txt.text= superHero.name
                for (item:String in superHero.users){
                    otherUser = "$otherUser , $item"
                }
                men.text = otherUser

                email =superHero.users[1]
                id = superHero.id

            }



        }
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when(v!!.id){
                R.id.Item_abrirchat -> {
                    val  activityIntent =  Intent(context,MensajesActivity::class.java)
                   activityIntent.putExtra("EMAIL",this.email)
                    activityIntent.putExtra("ID",this.id)
                    context.startActivity(activityIntent)
                }
            }

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