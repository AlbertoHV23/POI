package com.poi.camppus.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.R
import com.poi.camppus.models.ReferenciasFirebase
import com.poi.camppus.models.tbl_Chat
import com.poi.camppus.models.tbl_Mensajes
import com.poi.camppus.models.tbl_Usuarios
import java.util.*

class SendmessagesActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseDatabase.getInstance() //INTANCIA DE LA BASE DE DATOS
    val firebase  = FirebaseFirestore.getInstance()
    private  lateinit  var destinatario:EditText
    private  lateinit  var nameChat:EditText

    var grupo:MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sendmessages)
        auth = FirebaseAuth.getInstance()
        destinatario = findViewById(R.id.txt_sendDestinatario)
        nameChat = findViewById(R.id.nameChat)

        var btn_enviar: Button = findViewById(R.id.btn_sendmensaje)
        var btn_add: Button = findViewById(R.id.Agregar_grupo)

        btn_enviar.setOnClickListener(){
                enviarMensaje()
                finish()


        }
        btn_add.setOnClickListener(){
            grupo.add(destinatario.text.toString())
            Toast.makeText(this, "Added to ${destinatario.text.toString()} to group", Toast.LENGTH_SHORT).show()


        }




    }


    private fun enviarMensaje() {
        var chatId= UUID.randomUUID()
        val otherUser = destinatario.text.toString()
        val users = listOf(auth.currentUser.email, otherUser)
        var chat: tbl_Chat? = null


        grupo.add(auth.currentUser.email)

            chat = tbl_Chat(
                    id = chatId.toString(),
                    name = nameChat.text.toString(),
                    users = grupo
            )




        firebase.collection(ReferenciasFirebase.CHATS.toString()).document(chatId.toString()).set(chat)
        if (grupo !=null){
            for (item:String in grupo){
                var userRef = firebase.collection(ReferenciasFirebase.USERS.toString()).document(item)
                userRef.collection(ReferenciasFirebase.CHATS.toString()).document(chatId.toString()).set(chat)
                if (item != auth.currentUser.email){
                    val tblUsuarios:tbl_Usuarios = tbl_Usuarios("","","",item,"","https://firebasestorage.googleapis.com/v0/b/camppus-224af.appspot.com/o/logoDeafult.jpg?alt=media&token=2c018c0d-340b-4853-8fb6-7fe71f2287d7","Desconectado")
                    userRef.set(tblUsuarios)
                }

            }
        }
        destinatario.setText("")
    }


}