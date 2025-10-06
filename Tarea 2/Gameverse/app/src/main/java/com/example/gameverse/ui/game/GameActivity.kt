package com.example.gameverse.ui.game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gameverse.R

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Toolbar
        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Lee extras
        val gameId   = intent.getStringExtra(EXTRA_GAME_ID) ?: return
        val gameName = intent.getStringExtra(EXTRA_GAME_TITLE).orEmpty()
        if (gameName.isNotBlank()) title = gameName else title = "Juego"

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GameFragment.newInstance(gameId))
                .commit()
        }
    }

    companion object {
        const val EXTRA_GAME_ID = "extra_game_id"
        const val EXTRA_GAME_TITLE = "extra_game_title"
    }
}

