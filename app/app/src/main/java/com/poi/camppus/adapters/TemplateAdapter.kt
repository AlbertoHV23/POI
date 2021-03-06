package com.poi.camppus.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.poi.camppus.R
import com.poi.camppus.models.tbl_posts


class TemplateAdapter(val context: Context, var LISTA:List<tbl_posts>): RecyclerView.Adapter<TemplateAdapter.Holder>() {
    inner class Holder(val view: View):RecyclerView.ViewHolder(view), View.OnClickListener{

        fun render(superHero: tbl_posts) {
            if (superHero != null){


            }



        }
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

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