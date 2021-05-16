package com.poi.camppus.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.activities.GroupPageActivity
import com.poi.camppus.R
import com.poi.camppus.models.ReferenciasFirebase
import com.poi.camppus.models.tbl_groups
import com.poi.camppus.models.tbl_posts
import com.squareup.picasso.Picasso

class PostAdapter (val context: Context, var LISTA:List<tbl_posts>): RecyclerView.Adapter<PostAdapter.Holder>(){
    private lateinit var auth: FirebaseAuth
    val firebase  = FirebaseFirestore.getInstance();
    var imagen:String= ""

    inner class Holder(val view: View):RecyclerView.ViewHolder(view), View.OnClickListener{

        fun render(superHero: tbl_posts) {
            var txt_post:TextView = view.findViewById(R.id.textView14)
            var txt_user:TextView = view.findViewById(R.id.textView12)
            var txt_date:TextView = view.findViewById(R.id.textView13)
            var img:ImageView = view.findViewById(R.id.imageView4)



            val userRef = firebase.collection(ReferenciasFirebase.USERS.toString()).document(superHero.usuario)
            userRef.get()
                    .addOnSuccessListener {

                        imagen = it.get("image").toString()
                        txt_post.text = superHero.post
                        txt_user.text = superHero.usuario
                        txt_date.text = superHero.date

                        Picasso.get()
                                .load(imagen)
                                .resize(500, 500)
                                .centerCrop()
                                .into(img)
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
        return Holder(layoutInflater.inflate(R.layout.item_lista_post,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.render(LISTA[position])
    }

    override fun getItemCount(): Int {
        return LISTA.size
    }



}