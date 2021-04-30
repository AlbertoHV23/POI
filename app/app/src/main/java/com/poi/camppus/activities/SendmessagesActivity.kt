package com.poi.camppus.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.alonsodelcid.multichat.models.tbl_Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.R
import com.poi.camppus.models.ReferenciasFirebase
import com.poi.camppus.models.tbl_Mensajes
import java.util.*

class SendmessagesActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseDatabase.getInstance() //INTANCIA DE LA BASE DE DATOS
    val firebase  = FirebaseFirestore.getInstance();

    //TABLAS
    private val MessagesRef = db.getReference("Messages") //PARA METER IMFORMACION

    private  lateinit  var destinatario:EditText

    private  lateinit var mensajes: tbl_Mensajes
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sendmessages)

        auth = FirebaseAuth.getInstance()

        destinatario = findViewById(R.id.txt_sendDestinatario)
        //var contenido:EditText = findViewById(R.id.txt_contenido_enviarMensaje)
        var btn_enviar: Button = findViewById(R.id.btn_sendmensaje)

        obtenerListaChats()




        btn_enviar.setOnClickListener(){

           enviarMensaje()
            finish()
        }



    }
    private fun  obtenerListaChats(){
        val userRef = firebase.collection(ReferenciasFirebase.CHATS.toString()).document(auth.currentUser.email)
        userRef.collection(ReferenciasFirebase.MESSAGES.toString()).get()
                .addOnSuccessListener { document ->
                    var listChats = document.toObjects(tbl_Mensajes::class.java)

                }

    }

    private fun enviarMensaje() {
        var chatId= UUID.randomUUID()
        val otherUser = destinatario.text.toString()
        val users = listOf(auth.currentUser.email, otherUser)
        val chat = tbl_Chat(
                id = chatId.toString(),
                name = "Chat con $otherUser",
                users = users
        )

        firebase.collection(ReferenciasFirebase.CHATS.toString()).document(chatId.toString()).set(chat)
        firebase.collection(ReferenciasFirebase.USERS.toString()).document(auth.currentUser.email).collection(ReferenciasFirebase.CHATS.toString()).document(chatId.toString()).set(chat)
        firebase.collection(ReferenciasFirebase.USERS.toString()).document(otherUser).collection(ReferenciasFirebase.CHATS.toString()).document(chatId.toString()).set(chat)

    }


}