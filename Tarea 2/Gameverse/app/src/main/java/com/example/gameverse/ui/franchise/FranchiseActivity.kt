package com.example.gameverse.ui.franchise

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gameverse.R

class FranchiseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_franchise)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val genreName = intent.getStringExtra(EXTRA_GENRE_NAME).orEmpty()
        title = if (genreName.isNotBlank()) genreName else "Franquicias"

        // <- si no viene el extra, dejamos "" y el fragment mostrará TODAS las franquicias
        val genreId = intent.getStringExtra(EXTRA_GENRE_ID).orEmpty()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FranchiseFragment.newInstance(genreId))
                .commit()
        }
    }

    companion object {
        const val EXTRA_GENRE_ID = "extra_genre_id"
        const val EXTRA_GENRE_NAME = "extra_genre_name"
    }
}
