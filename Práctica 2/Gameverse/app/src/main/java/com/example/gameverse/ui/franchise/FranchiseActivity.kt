package com.example.gameverse.ui.franchise

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.gameverse.R
import com.example.gameverse.util.ThemeManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.switchmaterial.SwitchMaterial

class FranchiseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.applySaved(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_franchise)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Título = nombre del género
        title = intent.getStringExtra(EXTRA_GENRE_NAME).orEmpty()

        // Fragment de franquicias
        val genreId = intent.getStringExtra(EXTRA_GENRE_ID).orEmpty()
        if (supportFragmentManager.findFragmentByTag("franchise_list") == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FranchiseFragment.newInstance(genreId), "franchise_list")
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_theme_switch, menu)
        val item = menu.findItem(R.id.action_theme_switch)
        val sw = item?.actionView?.findViewById<SwitchMaterial>(R.id.switchTheme)
        sw?.apply {
            isChecked = (ThemeManager.current(this@FranchiseActivity) == ThemeManager.Mode.DARK)
            setOnCheckedChangeListener { _, dark ->
                ThemeManager.apply(if (dark) ThemeManager.Mode.DARK else ThemeManager.Mode.LIGHT, this@FranchiseActivity)
                recreate()
            }
        }
        return true
    }

    companion object {
        const val EXTRA_GENRE_ID = "extra_genre_id"
        const val EXTRA_GENRE_NAME = "extra_genre_name"
    }
}
