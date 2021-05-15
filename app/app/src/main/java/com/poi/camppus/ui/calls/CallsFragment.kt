package com.poi.camppus.ui.calls

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.CreateAssigmentActivity
import com.poi.camppus.R
import com.poi.camppus.activities.SendmessagesActivity
import com.poi.camppus.adapters.ListaChatAdapter
import com.poi.camppus.adapters.ListaUsuariosAdapter
import com.poi.camppus.models.ReferenciasFirebase
import com.poi.camppus.models.tbl_Chat
import com.poi.camppus.models.tbl_Usuarios

class CallsFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    lateinit var MisMensajes: List<tbl_Usuarios>
    val firebase  = FirebaseFirestore.getInstance()


    private var context2: Context? = null
    private var adapter: ListaUsuariosAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root =  inflater.inflate(R.layout.fragment_calls, container, false)
        auth = FirebaseAuth.getInstance()

        val rvChat: RecyclerView =root.findViewById<RecyclerView>(R.id.rv_losta_usuarios)
        rvChat.layoutManager = LinearLayoutManager(this.context2!!)
        val userRef = firebase.collection(ReferenciasFirebase.USERS.toString())
         userRef.get()
            .addOnSuccessListener { document ->
                MisMensajes= document.toObjects(tbl_Usuarios::class.java)
                this.adapter = ListaUsuariosAdapter(this.context2!!, MisMensajes)
                rvChat.adapter = this.adapter
            }

        userRef.addSnapshotListener(){
                messages,error ->
            if (error == null){
                messages?.let {  MisMensajes= it.toObjects(tbl_Usuarios::class.java)
                    this.adapter = ListaUsuariosAdapter(this.context2!!, MisMensajes)
                    rvChat.adapter = this.adapter
                }
            }
        }







        return root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context2 = context
    }



}