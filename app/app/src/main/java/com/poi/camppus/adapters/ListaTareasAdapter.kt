package com.poi.camppus.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.AssigmentActivity
import com.poi.camppus.activities.MensajesActivity
import com.poi.camppus.R
import com.poi.camppus.activities.GroupPageActivity
import com.poi.camppus.models.*
import com.squareup.picasso.Picasso

class ListaTareasAdapter(val context: Context, var LISTA:List<tbl_asssigments>): RecyclerView.Adapter<ListaTareasAdapter.Holder>(){
    private lateinit var auth: FirebaseAuth
    val firebase  = FirebaseFirestore.getInstance();
    inner class Holder(val view: View):RecyclerView.ViewHolder(view), View.OnClickListener{
        lateinit var  email:String
        lateinit var id:String
        lateinit var id_grupo:String


        fun render(superHero: tbl_asssigments) {
            var title: TextView = view.findViewById(R.id.textView4)
            var valor: TextView = view.findViewById(R.id.textView15)


            if (superHero != null){

                title.text = superHero.title
                valor.text = "${superHero.puntos} / ${superHero.valor}"
                id = superHero.id
                id_grupo = superHero.team




            }



        }
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when(v!!.id){
                R.id.item_id_lista_tareas ->{
                    val  activityIntent =  Intent(context, AssigmentActivity::class.java)
                    activityIntent.putExtra("ID_TAREA",this.id)
                    activityIntent.putExtra("ID_GRUPO",this.id_grupo)
                    context.startActivity(activityIntent)
                }

            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.item_lista_tareas,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.render(LISTA[position])
    }

    override fun getItemCount(): Int {
        return LISTA.size
    }



}