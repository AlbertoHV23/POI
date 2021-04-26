package com.poi.camppus.ui.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alonsodelcid.multichat.models.tbl_Chat
import com.google.android.material.snackbar.Snackbar
import com.poi.camppus.Adapter
import com.poi.camppus.R
import com.poi.camppus.activities.SendmessagesActivity

class ChatsFragment : Fragment() {
    var chats :List<tbl_Chat> = listOf(
            tbl_Chat("1","alberto", listOf("sd")),
            tbl_Chat("1","daniel", listOf("sd")),
            tbl_Chat("1","becerra", listOf("sd")),
            tbl_Chat("1","donato", listOf("sd"))
    )

    private var context2:Context? = null
    private var adapter:Adapter? = null


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        var root =  inflater.inflate(R.layout.fragment_chat, container, false)

        val rvChat:RecyclerView =root.findViewById<RecyclerView>(R.id.rv_lista_chats)
        rvChat.layoutManager = LinearLayoutManager(this.context2!!)
        this.adapter = Adapter(this.context2!!, chats)
        rvChat.adapter = this.adapter

        val fab: View = root.findViewById(R.id.fb)
        fab.setOnClickListener { view ->
            val intent = Intent (getActivity(), SendmessagesActivity::class.java)
            startActivity(intent)
        }




        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context2 = context
    }




}