package com.poi.camppus.ui.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.CreateAssigmentActivity
import com.poi.camppus.adapters.ListaChatAdapter
import com.poi.camppus.R
import com.poi.camppus.activities.SendmessagesActivity
import com.poi.camppus.models.ReferenciasFirebase
import com.poi.camppus.models.tbl_Chat

class ChatsFragment : Fragment() {
     lateinit var auth: FirebaseAuth
     lateinit var MisMensajes: List<tbl_Chat>
     val firebase  = FirebaseFirestore.getInstance()


    private var context2:Context? = null
    private var adapter: ListaChatAdapter? = null
    private  lateinit  var destinatario: EditText


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        var root =  inflater.inflate(R.layout.fragment_chat, container, false)
        auth = FirebaseAuth.getInstance()

        val rvChat:RecyclerView =root.findViewById<RecyclerView>(R.id.rv_lista_chats)
        rvChat.layoutManager = LinearLayoutManager(this.context2!!)
        val userRef = firebase.collection(ReferenciasFirebase.USERS.toString()).document(auth.currentUser.email)
        userRef.collection(ReferenciasFirebase.CHATS.toString()).get()
                .addOnSuccessListener { document ->
                    MisMensajes= document.toObjects(tbl_Chat::class.java)
                        this.adapter = ListaChatAdapter(this.context2!!, MisMensajes)
                        rvChat.adapter = this.adapter
                }


        userRef.collection(ReferenciasFirebase.CHATS.toString()).addSnapshotListener(){
            messages,error ->
            if (error == null){
                messages?.let {MisMensajes= it.toObjects(tbl_Chat::class.java)
                    this.adapter = ListaChatAdapter(this.context2!!, MisMensajes)
                    rvChat.adapter = this.adapter }
            }
        }


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