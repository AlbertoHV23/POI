package com.poi.camppus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alonsodelcid.multichat.models.tbl_Chat

class test : AppCompatActivity() {
    var chats :List<tbl_Chat> = listOf(
        tbl_Chat("1","alberto", listOf("sd")),
        tbl_Chat("1","daniel", listOf("sd"))
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initRecycler()
    }
    fun initRecycler(){
        var rv_superhero = findViewById<RecyclerView>(R.id.recycler)

        rv_superhero.layoutManager   = LinearLayoutManager(this)
        val adapter = Adapter(this, chats)
        rv_superhero.adapter = adapter
    }
}