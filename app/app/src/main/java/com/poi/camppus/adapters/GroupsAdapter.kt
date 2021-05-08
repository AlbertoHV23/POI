package com.poi.camppus.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.activities.GroupPageActivity
import com.poi.camppus.R
import com.poi.camppus.models.tbl_groups

class GroupsAdapter (val context: Context, var LISTA:List<tbl_groups>): RecyclerView.Adapter<GroupsAdapter.Holder>(){
    private lateinit var auth: FirebaseAuth
    val firebase  = FirebaseFirestore.getInstance();
    inner class Holder(val view: View):RecyclerView.ViewHolder(view), View.OnClickListener{
        lateinit var  email:String
        lateinit var id:String
        fun render(superHero: tbl_groups) {
            if (superHero != null){
                var team: TextView = view?.findViewById(R.id.txt_name_grupo)
                //var img: ImageView = view?.findViewById(R.id.img_grupo)
                team.text = superHero.name


            }



        }
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when(v!!.id){
                R.id.item_lista_groups ->{
                    val  activityIntent =  Intent(context, GroupPageActivity::class.java)
                    context.startActivity(activityIntent)
                }

            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.item_lista_grupos,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.render(LISTA[position])
    }

    override fun getItemCount(): Int {
        return LISTA.size
    }



}