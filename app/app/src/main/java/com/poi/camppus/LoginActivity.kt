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
        var btn_logear :Button = findViewById(R.id.btn_logear)

        btn_activitySignIn.setOnClickListener(){
            goToActivitySingIN()
        }
        btn_logear.setOnClickListener(){
            goToMainNavigationActivity()
        }
    }

    fun goToActivitySingIN(){
        startActivity(Intent(this,SignInActivity::class.java))
    }

    fun goToMainNavigationActivity(){
        startActivity(Intent(this,MainNavigationActivity::class.java))
    }

}