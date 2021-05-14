package com.poi.camppus.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.R
import com.poi.camppus.models.ReferenciasFirebase
import com.poi.camppus.models.tbl_Usuarios


class   LoginActivity : AppCompatActivity() {
    companion object{
        private const val RC_SIGN_IN = 120
    }

    private lateinit var auth:FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var txt_email: EditText
    lateinit var txt_password : EditText
    private val db = FirebaseDatabase.getInstance()
    val firebase  = FirebaseFirestore.getInstance();


    lateinit var email:String
    lateinit var pasword:String

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1500)
        setTheme(R.style.Theme_Camppus_login)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()

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
            signIn()
        }

    }

    fun goToActivitySingIN(){
        startActivity(Intent(this, SignInActivity::class.java))
    }


    fun validarRegistro(){

        email = txt_email.text.toString()
        pasword = txt_password.text.toString()

        if (email.isNotEmpty() && pasword.isNotEmpty()) Login()
        else ShowAlert("Empty Fields", "Fill in all the fields to be able to register")
    }

    fun Login(){
        auth.signInWithEmailAndPassword(email, pasword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        showHome()
                        firebase.collection(ReferenciasFirebase.USERS.toString()).document(email).update("estado","Disponible")

                    }
                    else ShowAlert("Error", "Usuario o contraseña incorrecta")
                }

    }

    fun showHome(){
        val intent:Intent = Intent(this, MainNavigationActivity::class.java)
        intent.putExtra("EMAIL", auth.currentUser.email)
        startActivity(intent)
        finish()

    }

    fun ShowAlert(title: String, msg: String) {
        val simpleDialog =  AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setIcon(R.drawable.ic_baseline_error_24)
                .setPositiveButton("Retry"){ _, _ ->
                    Toast.makeText(this, "Try again", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("Cancel"){ _, _->
                    Toast.makeText(this, "Cancel add user", Toast.LENGTH_LONG).show()
                }.create()

        simpleDialog.show()
    }

    //GOOGLE
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                }
            }


        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Signin", "signInWithCredential:success")
                        val user = auth.currentUser
                        showHome()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Signin", "signInWithCredential:failure", task.exception)
                    }
                }
    }


    private fun CheckUser(){
        val currentUser = auth.currentUser
        if (currentUser !=null){
            showHome()

        }
    }









}