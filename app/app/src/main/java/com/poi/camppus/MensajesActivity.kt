package com.poi.camppus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alonsodelcid.multichat.models.tbl_Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ServerValue
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.adapters.MensajesAdapter
import com.poi.camppus.models.ReferenciasFirebase
import com.poi.camppus.models.tbl_Mensajes
import java.util.*

class MensajesActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = FirebaseDatabase.getInstance() //INTANCIA DE LA BASE DE DATOS
    val firebase  = FirebaseFirestore.getInstance();


    private lateinit var _Mensaje:EditText
    private lateinit var _id:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        auth = FirebaseAuth.getInstance()

        var ema=  intent.getStringExtra("EMAIL")
        var uid=  intent.getStringExtra("ID")
        var username:TextView = findViewById(R.id.txt_UsernameMensaje)
        username.text = ema

        _Mensaje = findViewById(R.id.txt_mensaje_main)
        if (uid != null) {
            _id = uid
        }


        val btn_send:Button = findViewById(R.id.btn_enviarmensajemain)


        btn_send.setOnClickListener(){
            enviarMensaje()
        }
        val userRef = firebase.collection(ReferenciasFirebase.CHATS.toString()).document(_id)

        userRef.collection(ReferenciasFirebase.MESSAGES.toString()).orderBy("dob",com.google.firebase.firestore.Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener { document ->
                    var listChats = document.toObjects(tbl_Mensajes::class.java)
                    var rv = findViewById<RecyclerView>(R.id.rv_ListaMensajes)

                    rv.layoutManager   = LinearLayoutManager(this)
                    val adapter = MensajesAdapter(this, listChats)
                    rv.adapter = adapter



                }
        userRef.collection(ReferenciasFirebase.MESSAGES.toString()).orderBy("dob",com.google.firebase.firestore.Query.Direction.ASCENDING)
                .addSnapshotListener(){
            messages,error ->
            if (error == null){
                messages?.let { var listChats = it.toObjects(tbl_Mensajes::class.java)
                    var rv = findViewById<RecyclerView>(R.id.rv_ListaMensajes)

                    rv.layoutManager   = LinearLayoutManager(this)
                    val adapter = MensajesAdapter(this, listChats)
                    rv.adapter = adapter }
            }
        }

    }

    private fun enviarMensaje() {
        val mensaje = _Mensaje.text.toString()
        val txt_mensaje_main:EditText = findViewById(R.id.txt_mensaje_main)

        val chat = tbl_Mensajes(
                message = mensaje,
                from = auth.currentUser.email


        )

        firebase.collection(ReferenciasFirebase.CHATS.toString()).document(_id).collection(ReferenciasFirebase.MESSAGES.toString()).document().set(chat)
        txt_mensaje_main.setText("")
    }
}