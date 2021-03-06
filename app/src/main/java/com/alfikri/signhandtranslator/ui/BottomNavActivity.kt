package com.alfikri.signhandtranslator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.alfikri.signhandtranslator.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavActivity : AppCompatActivity() {

    private var doubleBackExit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)

        supportActionBar?.hide()

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navController = findNavController(R.id.nav_host_fragment2)

        bottomNav.setupWithNavController(navController)

        bottomNav.itemIconTintList = null
    }

    override fun onBackPressed() {
        if (doubleBackExit){
            finishAffinity()
            return
        }

        this.doubleBackExit = true

        Handler().postDelayed(
            { doubleBackExit = false }, 2000)
    }

}