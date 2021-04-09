package com.poi.camppus

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.poi.camppus.models.tbl_Usuarios

class LoginActivity : AppCompatActivity() {

    private var auth: FirebaseAuth =  Firebase.auth
    private var GoogleSignInClient: GoogleSignInClient =

    lateinit var txt_email: EditText
    lateinit var txt_password : EditText

    //

    lateinit var email:String
    lateinit var pasword:String


    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1500)
        setTheme(R.style.Theme_Camppus_login)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        var btn_activitySignIn = findViewById<Button>(R.id.btn_activitySignIn)
        var btn_logear :Button = findViewById(R.id.btn_logear)
        var btn_logearGoogle :Button = findViewById(R.id.btn_login_google)

        txt_email = findViewById(R.id.txt_loginEmail)
        txt_password  = findViewById(R.id.txt_loginPassword)


        btn_activitySignIn.setOnClickListener(){
            goToActivitySingIN()
        }

        btn_logear.setOnClickListener(){
            validarRegistro()
        }

        btn_logearGoogle.setOnClickListener(){

        }




    }

    fun goToActivitySingIN(){
        startActivity(Intent(this,SignInActivity::class.java))
    }


    fun validarRegistro(){

        email = txt_email.text.toString()
        pasword = txt_password.text.toString()

        if (email.isNotEmpty() && pasword.isNotEmpty()) Login()
        else ShowAlert("Empty Fields","Fill in all the fields to be able to register")
    }

    fun Login(){
        auth.signInWithEmailAndPassword(email, pasword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) showHome()
                    else ShowAlert("Error","Usuario o contraseña incorrecta")
                }

    }

    fun showHome(){
        val intent:Intent = Intent(this,MainNavigationActivity::class.java)
        intent.putExtra("email",email)
        startActivity(intent)

    }

    fun ShowAlert(title:String,msg:String,) {

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

    fun sesion(){
        val prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        val email = prefs.getString("email","")
        if (email!!.isNotEmpty()){
            showHome()
        }
    }









}