package com.poi.camppus.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.poi.camppus.R
import com.poi.camppus.models.ReferenciasFirebase
import com.poi.camppus.models.tbl_Usuarios
import java.util.*


class SignInActivity : AppCompatActivity() {
    lateinit var txt_names:EditText
    lateinit var txt_surnames:EditText
    lateinit var txt_email:EditText
    lateinit var txt_password :EditText
    lateinit var btn_registrar :Button

    private lateinit var auth: FirebaseAuth
    private val db = FirebaseDatabase.getInstance() //INTANCIA DE LA BASE DE DATOS
    val firebase  = FirebaseFirestore.getInstance();

    lateinit var names:String
    lateinit var surnames:String
    lateinit var email:String
    lateinit var pasword:String

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Camppus_login)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = Firebase.auth
        txt_names  = findViewById(R.id.txt_signNames)
        txt_surnames = findViewById(R.id.txt_signSurnames)
        txt_email = findViewById(R.id.txt_signEmail)
        txt_password  = findViewById(R.id.txt_signPassword)
        btn_registrar = findViewById(R.id.btn_registrar)

        btn_registrar.setOnClickListener(){
            validarRegistro()
        }




    }

    fun validarRegistro(){
        names = txt_names.text.toString()
        surnames = txt_surnames.text.toString()
        email = txt_email.text.toString()
        pasword = txt_password.text.toString()

        if (names.isNotEmpty() && surnames.isNotEmpty() && email.isNotEmpty() && pasword.isNotEmpty()){
            if (pasword.length >= 6 ){
                RegistarUsuario()
            }
            else{
                simpleDialog("Incorrect Password","enter a correct password")
            }
        }
        else{
        simpleDialog("Empty Fields","Fill in all the fields to be able to register")

        }
    }

    fun RegistarUsuario(){
        auth.createUserWithEmailAndPassword(email, pasword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val usuario = tbl_Usuarios(id = "",names = names,surname = surnames,emails =email ,password = pasword,"img")
                    //InsertUduario(usuario)
                    Toast.makeText(baseContext, "User added.", Toast.LENGTH_SHORT).show()
                    showHome(usuario)

                }
                else {
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }

    }


    private fun simpleDialog(title:String,msg:String,) {

        val simpleDialog =  AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg )
                .setIcon(R.drawable.ic_baseline_error_24)
                .setPositiveButton("Retry"){ _,_ ->

                    Toast.makeText(this,"Try again", Toast.LENGTH_LONG).show()

                }
                .setNegativeButton("Cancel"){_,_->

                    Toast.makeText(this,"Cancel add user", Toast.LENGTH_LONG).show()
                }.create()

        simpleDialog.show()
    }

    private fun InsertUduario(user: tbl_Usuarios) {
        user.id = UUID.randomUUID().toString()
        firebase.collection(ReferenciasFirebase.USERS.toString()).document(user.id).set(user)
        //firebase.collection("USERS").add(user)
    }



    fun showHome(usuario: tbl_Usuarios){
        val intent:Intent = Intent(this, MainNavigationActivity::class.java)
        intent.putExtra("email",email)
        startActivity(intent)
        finish()
    }

}