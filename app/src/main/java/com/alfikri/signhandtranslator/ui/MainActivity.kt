package com.alfikri.signhandtranslator.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alfikri.signhandtranslator.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

        checkUser()
    }

    private fun checkUser(){
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null){
            val intent = Intent(this, BottomNavActivity::class.java)
            startActivity(intent)
        }
    }
}