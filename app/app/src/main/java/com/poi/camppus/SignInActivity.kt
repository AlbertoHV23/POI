package com.poi.camppus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Camppus_login)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }
}