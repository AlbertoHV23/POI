package com.poi.camppus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.poi.camppus.models.tbl_Mensajes

class SendmessagesActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseDatabase.getInstance() //INTANCIA DE LA BASE DE DATOS

    //TABLAS
    private val MessagesRef = db.getReference("Messages") //PARA METER IMFORMACION

    private  lateinit var mensajes: tbl_Mensajes
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sendmessages)

        var destinatario:TextView = findViewById(R.id.txt_destinatario)
        var mensaje:EditText = findViewById(R.id.iniciarConversacion)
        var btn_enviar: Button = findViewById(R.id.btnEnviarMensaje)

        btn_enviar.setOnClickListener(){
            val ObjetoMensaje = tbl_Mensajes("",mensaje.text.toString(),"correo@gmail.com",destinatario.text.toString(),ServerValue.TIMESTAMP)
           enviarMensaje(ObjetoMensaje)
        }


    }

    private fun enviarMensaje(mensaje: tbl_Mensajes) {
        val MensajeFirebase = MessagesRef.push()
        mensaje.id = MensajeFirebase.key ?: ""
        MensajeFirebase.setValue(mensaje)
    }
}