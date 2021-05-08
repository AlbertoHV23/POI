package com.poi.camppus.ui.groups

import android.content.Context
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
import com.poi.camppus.R
import com.poi.camppus.adapters.GroupsAdapter
import com.poi.camppus.adapters.ListaChatAdapter
import com.poi.camppus.adapters.MensajesAdapter
import com.poi.camppus.models.tbl_groups

class GroupsFragment : Fragment() {
     var grupos :List<tbl_groups> = listOf(
            tbl_groups("1","sdsd", listOf("sd")),
            tbl_groups("1","dasdsdsdniel", listOf("sd"))
    )

    private var context2: Context? = null
    private var adapter: GroupsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater
                              , container: ViewGroup?,
                              savedInstanceState: Bundle?)
    : View? {
        // Inflate the layout for this fragment
        var root =  inflater.inflate(R.layout.fragment_groups, container, false)

        val rvChat:RecyclerView =root.findViewById(R.id.rv_equipos)
        rvChat.layoutManager = LinearLayoutManager(this.context2!!)
        this.adapter = GroupsAdapter(this.context2!!, grupos)
        rvChat.adapter = this.adapter




        return root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context2 = context
    }
}