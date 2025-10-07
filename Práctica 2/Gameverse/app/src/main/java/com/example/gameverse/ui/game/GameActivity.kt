package com.example.gameverse.ui.game

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.gameverse.R
import com.example.gameverse.util.ThemeManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.switchmaterial.SwitchMaterial

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.applySaved(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Título del juego (y el fragment también lo refuerza)
        title = intent.getStringExtra(EXTRA_GAME_TITLE).orEmpty()

        val gameId = intent.getStringExtra(EXTRA_GAME_ID) ?: return
        if (supportFragmentManager.findFragmentByTag("game_detail") == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GameFragment.newInstance(gameId), "game_detail")
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_theme_switch, menu)
        val item = menu.findItem(R.id.action_theme_switch)
        val sw = item?.actionView?.findViewById<SwitchMaterial>(R.id.switchTheme)
        sw?.apply {
            isChecked = (ThemeManager.current(this@GameActivity) == ThemeManager.Mode.DARK)
            setOnCheckedChangeListener { _, dark ->
                ThemeManager.apply(if (dark) ThemeManager.Mode.DARK else ThemeManager.Mode.LIGHT, this@GameActivity)
                recreate()
            }
        }
        return true
    }

    companion object {
        const val EXTRA_GAME_ID = "extra_game_id"
        const val EXTRA_GAME_TITLE = "extra_game_title"
    }
}
