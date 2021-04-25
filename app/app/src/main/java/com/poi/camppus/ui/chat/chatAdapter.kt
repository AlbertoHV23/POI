package com.poi.camppus.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.poi.camppus.R

class chatAdapter:RecyclerView.Adapter<chatAdapter.chatViewHolder>() {
    class chatViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){
        fun render(){



        }
    }
    var listchat:List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chatViewHolder {
       return chatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_listchats,parent,false))
    }

    override fun onBindViewHolder(holder: chatViewHolder, position: Int) {
        var name =listchat[position]

    }

    override fun getItemCount(): Int {
        return listchat.size

    }
}