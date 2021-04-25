package com.poi.camppus.ui.chat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poi.camppus.MainNavigationActivity
import com.poi.camppus.R
import com.poi.camppus.SendmessagesActivity
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatsFragment : Fragment() {
    lateinit var btn:Button //Boton para empezar chat
    lateinit var rv :RecyclerView //Recycler view del historia de mensajes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_chat, container, false)
        val puto:Button = root.findViewById(R.id.puto)

        rv.layoutManager = LinearLayoutManager(getActivity())
        rv.adapter



        puto.setOnClickListener(){
            val intent = Intent (getActivity(), SendmessagesActivity::class.java)
            getActivity()?.startActivity(intent)
          println("becerra es puto")
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }



    }
