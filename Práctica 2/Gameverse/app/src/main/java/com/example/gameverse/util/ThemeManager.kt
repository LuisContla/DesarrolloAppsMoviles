package com.example.gameverse.util

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {
    private const val PREFS = "settings"
    private const val KEY = "theme_mode" // 1=light, 2=dark

    enum class Mode(val value: Int) { LIGHT(1), DARK(2) }

    fun current(context: Context): Mode =
        when (context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getInt(KEY, 1)) {
            2 -> Mode.DARK
            else -> Mode.LIGHT
        }

    fun apply(mode: Mode, context: Context? = null) {
        val night = when (mode) {
            Mode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            Mode.DARK  -> AppCompatDelegate.MODE_NIGHT_YES
        }
        AppCompatDelegate.setDefaultNightMode(night)
        context?.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            ?.edit()?.putInt(KEY, mode.value)?.apply()
    }

    /** Llama esto al principio de onCreate() de cada Activity. */
    fun applySaved(context: Context) = apply(current(context))
}

