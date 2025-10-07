package com.example.gameverse.ui.game

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.gameverse.R
import com.example.gameverse.databinding.ActivityGamesBinding
import com.example.gameverse.util.ThemeManager
import com.google.android.material.switchmaterial.SwitchMaterial

class GamesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGamesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.applySaved(this)
        super.onCreate(savedInstanceState)
        binding = ActivityGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Título = nombre de la franquicia
        supportActionBar?.title = intent.getStringExtra(EXTRA_FRANCHISE_NAME).orEmpty()

        // Fragment con lista de juegos de la franquicia
        val franchiseId = intent.getStringExtra(EXTRA_FRANCHISE_ID).orEmpty()
        if (supportFragmentManager.findFragmentByTag("games_list") == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, GamesListFragment.newInstance(franchiseId), "games_list")
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_theme_switch, menu)
        val item = menu.findItem(R.id.action_theme_switch)
        val sw = item?.actionView?.findViewById<SwitchMaterial>(R.id.switchTheme)
        sw?.apply {
            isChecked = (ThemeManager.current(this@GamesActivity) == ThemeManager.Mode.DARK)
            setOnCheckedChangeListener { _, dark ->
                ThemeManager.apply(if (dark) ThemeManager.Mode.DARK else ThemeManager.Mode.LIGHT, this@GamesActivity)
                recreate()
            }
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_FRANCHISE_ID = "franchiseId"
        const val EXTRA_FRANCHISE_NAME = "franchiseName"
    }
}
