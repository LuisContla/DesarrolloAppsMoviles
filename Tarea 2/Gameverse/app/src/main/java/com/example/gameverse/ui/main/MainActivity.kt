package com.example.gameverse.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gameverse.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar
        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false) // sin botón atrás en raíz
        title = "Géneros"

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GenresFragment())
                .commit()
        }
    }
}
