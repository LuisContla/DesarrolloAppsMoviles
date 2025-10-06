package com.example.gameverse.ui.game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gameverse.databinding.ActivityGamesBinding

class GamesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGamesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val franchiseName = intent.getStringExtra(EXTRA_FRANCHISE_NAME) ?: "Franquicia"

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = franchiseName

        val franchiseId = intent.getStringExtra(EXTRA_FRANCHISE_ID) ?: ""
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, GamesListFragment.newInstance(franchiseId))
                .commit()
        }
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
