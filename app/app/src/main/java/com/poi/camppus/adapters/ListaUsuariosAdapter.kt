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
import com.poi.camppus.activities.MensajesActivity
import com.poi.camppus.R
import com.poi.camppus.models.tbl_Chat
import com.poi.camppus.models.tbl_Usuarios
import com.poi.camppus.models.tbl_groups
import com.poi.camppus.models.tbl_posts
import com.squareup.picasso.Picasso

class ListaUsuariosAdapter(val context: Context, var LISTA:List<tbl_Usuarios>): RecyclerView.Adapter<ListaUsuariosAdapter.Holder>(){
    private lateinit var auth: FirebaseAuth
    val firebase  = FirebaseFirestore.getInstance();
    inner class Holder(val view: View):RecyclerView.ViewHolder(view), View.OnClickListener{
        lateinit var  email:String
        lateinit var id:String
        var otherUser = ""
        var desti = ""
        var NAME = ""
        fun render(superHero: tbl_Usuarios) {
            var txt_name: TextView = view.findViewById(R.id.txt_item_lista_usuario_nombre)
            var txt_estado: TextView = view.findViewById(R.id.txt_estado)
            var img: ImageView = view?.findViewById(R.id.imageView6)





            if (superHero != null){

                txt_name.text = superHero.emails
                txt_estado.text = superHero.estado
                if (superHero.image != ""){

                    Picasso.get()
                            .load(superHero.image)
                            .resize(300, 300)
                            .centerCrop()
                            .into(img)

                }
                else{
                    Picasso.get()
                            .load("https://firebasestorage.googleapis.com/v0/b/camppus-224af.appspot.com/o/logoDeafult.jpg?alt=media&token=2c018c0d-340b-4853-8fb6-7fe71f2287d7")
                            .resize(300, 300)
                            .centerCrop()
                            .into(img)
                }



            }



        }
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when(v!!.id){

            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.item_lista_usuarios,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.render(LISTA[position])
    }

    override fun getItemCount(): Int {
        return LISTA.size
    }



}