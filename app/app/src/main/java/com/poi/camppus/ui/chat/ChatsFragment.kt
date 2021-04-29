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
import com.alonsodelcid.multichat.models.tbl_Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.poi.camppus.MensajesActivity
import com.poi.camppus.adapters.ListaChatAdapter
import com.poi.camppus.R
import com.poi.camppus.activities.SendmessagesActivity
import com.poi.camppus.models.ReferenciasFirebase
import java.util.*

class ChatsFragment : Fragment() {
     lateinit var auth: FirebaseAuth
     lateinit var MisMensajes: List<tbl_Chat>
    val firebase  = FirebaseFirestore.getInstance();
    var chats :List<tbl_Chat> = listOf(
            tbl_Chat("1","alberto", listOf("sd")),
            tbl_Chat("1","daniel", listOf("sd")),
            tbl_Chat("1","becerra", listOf("sd")),
            tbl_Chat("1","donato", listOf("sd"))
    )

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
        obtenerListaChats()
        val rvChat:RecyclerView =root.findViewById<RecyclerView>(R.id.rv_lista_chats)
        rvChat.layoutManager = LinearLayoutManager(this.context2!!)
        println(auth.currentUser.email.toString())
        val userRef = firebase.collection(ReferenciasFirebase.USERS.toString()).document(auth.currentUser.email)
        userRef.collection(ReferenciasFirebase.CHATS.toString()).get()
                .addOnSuccessListener { document ->
                    MisMensajes= document.toObjects(tbl_Chat::class.java)
                    this.adapter = ListaChatAdapter(this.context2!!, MisMensajes)
                    rvChat.adapter = this.adapter


                }






        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

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

    private fun  obtenerListaChats(){


    }

    private fun enviarMensaje() {
        val chatId = UUID.randomUUID().toString()
        val otherUser = destinatario.text.toString()
        val users = listOf(auth.currentUser.email, otherUser)
        val chat = tbl_Chat(
            id = chatId,
            name = "Chat con $otherUser",
            users = users
        )

        firebase.collection(ReferenciasFirebase.CHATS.toString()).document(chatId).set(chat)
        firebase.collection(ReferenciasFirebase.USERS.toString()).document(auth.currentUser.email).collection(ReferenciasFirebase.CHATS.toString()).document(chatId).set(chat)
        firebase.collection(ReferenciasFirebase.USERS.toString()).document(otherUser).collection(ReferenciasFirebase.CHATS.toString()).document(chatId).set(chat)

    }






}