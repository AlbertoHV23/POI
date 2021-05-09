package com.poi.camppus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ServerValue
import com.poi.camppus.adapters.ListaChatAdapter
import com.poi.camppus.adapters.MensajesAdapter
import com.poi.camppus.models.tbl_Chat
import com.poi.camppus.models.tbl_Mensajes

class test : AppCompatActivity() {
    var chats :List<tbl_Chat> = listOf(
        tbl_Chat("1","alberto", listOf("sd")),
        tbl_Chat("1","daniel", listOf("sd"))
    )
    var list: List<tbl_Mensajes> = listOf(

    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initRecycler()
    }
    fun initRecycler(){
        var rv_superhero = findViewById<RecyclerView>(R.id.rv_ListaMensajes)

        rv_superhero.layoutManager   = LinearLayoutManager(this)
        val adapter = MensajesAdapter(this, list)
        rv_superhero.adapter = adapter
    }
}