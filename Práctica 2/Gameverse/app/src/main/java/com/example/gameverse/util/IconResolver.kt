package com.example.gameverse.util

import android.content.Context
import android.util.Log
import androidx.annotation.DrawableRes
import java.text.Normalizer

object IconResolver {
    private const val TAG = "IconResolver"

    @DrawableRes
    fun resolveGenreIcon(
        context: Context,
        id: String,
        displayName: String?,
        @DrawableRes fallback: Int
    ): Int {
        val bases = buildBaseCandidates(id, displayName)
        val suffixes = listOf("_neon_v3_128", "_neon_v2_128", "_neon_256", "_neon", "")

        for (b in bases) {
            for (s in suffixes) {
                val name = if (s.isEmpty()) b else b + s
                val resId = context.resources.getIdentifier(name, "drawable", context.packageName)
                if (resId != 0) {
                    Log.d(TAG, "Icono encontrado: $name ($resId)")
                    return resId
                }
            }
        }
        Log.w(TAG, "No se encontró icono para id='$id' name='$displayName'. Fallback=$fallback")
        return fallback
    }

    // --- helpers ---

    private fun norm(raw: String): String {
        val nfd = Normalizer.normalize(raw, Normalizer.Form.NFD)
        val noAccents = nfd.replace("\\p{M}+".toRegex(), "")
        return noAccents
            .lowercase()
            .replace("[^a-z0-9]+".toRegex(), "_")
            .trim('_')
    }

    private fun stripPrefixes(s: String): String {
        return s.removePrefix("g_")
            .removePrefix("genre_")
            .removePrefix("gen_")
            .removePrefix("cat_")
    }

    private fun buildBaseCandidates(id: String, displayName: String?): List<String> {
        val set = linkedSetOf<String>()
        fun addAllOf(base: String) {
            set += base
            set += stripPrefixes(base)
        }

        // Desde id y nombre visible
        addAllOf(norm(id))
        displayName?.let { addAllOf(norm(it)) }

        // Si coincide con claves “conocidas”, añade sinónimos ES/EN
        val syn = mapOf(
            "accion" to listOf("accion", "action"),
            "plataformas" to listOf("plataformas", "platform", "platforms", "platformer", "platformers"),
            "estrategia" to listOf("estrategia", "strategy"),
            "carreras" to listOf("carreras", "racing"),
            "deportes" to listOf("deportes", "sports"),
            "lucha" to listOf("lucha", "fighting"),
            "aventura" to listOf("aventura", "adventure"),
            "shooter" to listOf("shooter", "fps", "tps"),
            "rpg" to listOf("rpg", "role_playing", "roleplaying")
        )
        val copy = set.toList()
        for (b in copy) {
            syn[b]?.let { set.addAll(it) }
        }
        return set.toList()
    }
}
