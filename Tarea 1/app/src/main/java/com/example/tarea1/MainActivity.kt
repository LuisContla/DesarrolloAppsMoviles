package com.example.tarea1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // Listener para los items del menú de navegación
        bottomNavigation.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_text_fields -> selectedFragment = TextFieldsFragment()
                R.id.nav_buttons -> selectedFragment = ButtonsFragment()
                R.id.nav_selection -> selectedFragment = SelectionControlsFragment()
                R.id.nav_lists -> selectedFragment = ListsFragment()
                R.id.nav_info -> selectedFragment = InfoElementsFragment()
            }

            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit() // Usar commit() aquí está bien para este caso.
            }
            true // Indica que el evento ha sido manejado
        }

        // Cargar el primer fragment por defecto al iniciar la app
        if (savedInstanceState == null) { // Evita recargar si la activity se recrea (ej. rotación)
            bottomNavigation.selectedItemId = R.id.nav_text_fields // Puedes cambiar cuál es el primero
        }
    }
}
