package com.example.gameverse.ui.main

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.gameverse.R
import com.example.gameverse.util.ThemeManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.applySaved(this)                 // << tema antes de inflar vistas
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        // Carga del fragment raíz (géneros), inmediata
        if (supportFragmentManager.findFragmentByTag("genres") == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GenresFragment(), "genres")
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_theme_switch, menu)
        // Enlaza el switch
        val item = menu.findItem(R.id.action_theme_switch)
        val sw = item?.actionView?.findViewById<SwitchMaterial>(R.id.switchTheme)
        sw?.apply {
            isChecked = (ThemeManager.current(this@MainActivity) == ThemeManager.Mode.DARK)
            setOnCheckedChangeListener { _, dark ->
                ThemeManager.apply(if (dark) ThemeManager.Mode.DARK else ThemeManager.Mode.LIGHT, this@MainActivity)
                recreate()
            }
        }
        return true
    }
}
