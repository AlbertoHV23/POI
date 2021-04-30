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
    val firebase  = FirebaseFirestore.getInstance()
    private  lateinit  var destinatario:EditText
    var grupo:MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sendmessages)
        auth = FirebaseAuth.getInstance()
        destinatario = findViewById(R.id.txt_sendDestinatario)

        var btn_enviar: Button = findViewById(R.id.btn_sendmensaje)
        var btn_add: Button = findViewById(R.id.Agregar_grupo)

        btn_enviar.setOnClickListener(){
           enviarMensaje()
            finish()
        }
        btn_add.setOnClickListener(){
            grupo.add(destinatario.text.toString())
            println(grupo)
        }




    }


    private fun enviarMensaje() {
        var chatId= UUID.randomUUID()
        val otherUser = destinatario.text.toString()
        val users = listOf(auth.currentUser.email, otherUser)

        grupo.add(auth.currentUser.email)
        val chat = tbl_Chat(
                id = chatId.toString(),
                name = "$otherUser",
                users = grupo
        )



        firebase.collection(ReferenciasFirebase.CHATS.toString()).document(chatId.toString()).set(chat)
        if (grupo !=null){
            for (item:String in grupo){
                firebase.collection(ReferenciasFirebase.USERS.toString()).document(item).collection(ReferenciasFirebase.CHATS.toString()).document(chatId.toString()).set(chat)
            }
        }

    }


}