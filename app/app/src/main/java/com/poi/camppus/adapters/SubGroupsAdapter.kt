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
import com.poi.camppus.SubGroupPageActivity
import com.poi.camppus.models.tbl_groups
import com.poi.camppus.models.tbl_subgroups
import com.squareup.picasso.Picasso

class SubGroupsAdapter (val context: Context, var LISTA:List<tbl_subgroups>): RecyclerView.Adapter<SubGroupsAdapter.Holder>(){
    private lateinit var auth: FirebaseAuth
    val firebase  = FirebaseFirestore.getInstance();
    inner class Holder(val view: View):RecyclerView.ViewHolder(view), View.OnClickListener{
        var NAMETEAM:String = ""
        lateinit var  email:String
        lateinit var id:String
        fun render(superHero: tbl_subgroups) {
            if (superHero != null){
                var team: TextView = view?.findViewById(R.id.txt_name_subgrupo)
                var imagen:ImageView =  view?.findViewById(R.id.img_subgrupo)
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/camppus-224af.appspot.com/o/IconTeams.jpg?alt=media&token=d1c3f480-9de1-438a-bd42-3046de17480a").into(imagen)

                team.text = superHero.name
                this.NAMETEAM =  superHero.name
                this.id = superHero.id


            }



        }
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when(v!!.id){
                R.id.item_lista_groups ->{
                    val  activityIntent =  Intent(context, SubGroupPageActivity::class.java)
                    activityIntent.putExtra("NAMETEAM",this.NAMETEAM)
                    activityIntent.putExtra("ID",this.id)
                    println(this.id)
                    context.startActivity(activityIntent)
                }

            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.item_lista_subgrupos,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.render(LISTA[position])
    }

    override fun getItemCount(): Int {
        return LISTA.size
    }



}