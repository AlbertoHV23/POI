package com.poi.camppus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        var btn_activitySignIn = findViewById<Button>(R.id.btn_activitySignIn)

        btn_activitySignIn.setOnClickListener(){
            goToActivitySingIN()
        }
    }

    fun goToActivitySingIN(){
        startActivity(Intent(this,SignInActivity::class.java))
    }

}