package com.example.practica5.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.practica5.R
import com.example.practica5.data.SessionStore
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val session by lazy { SessionStore(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        val navHost = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        val navController = navHost.navController

        // Conecta BottomNav con NavController
        bottomNav.setupWithNavController(navController)

        lifecycleScope.launch {
            // Si no hay token, ir a login
            val token = session.getToken()
            if (token.isNullOrBlank()) {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
                return@launch
            }

            val role = session.getRole() ?: "USER"
            val isAdmin = role == "ADMIN"

            if (!isAdmin) {
                // Quita el botón Admin del menú
                bottomNav.menu.removeItem(R.id.adminFragment)
                bottomNav.menu.removeItem(R.id.itemsFragment) // <- Mi API solo admins

                // Si por alguna razón estás parado en Admin, te manda a Home
                if (navController.currentDestination?.id == R.id.adminFragment) {
                    navController.navigate(R.id.homeFragment)
                }
            }
        }
    }
}
