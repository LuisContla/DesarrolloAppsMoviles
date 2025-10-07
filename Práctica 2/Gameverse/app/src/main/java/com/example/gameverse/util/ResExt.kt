package com.example.gameverse.util

import android.content.Context
import androidx.annotation.DrawableRes
import com.example.gameverse.R

object ResExt {

    /**
     * Busca un drawable por nombre. Si no existe, regresa 0.
     */
    fun idFromName(ctx: Context, name: String): Int {
        return ctx.resources.getIdentifier(name, "drawable", ctx.packageName)
    }

    /**
     * Devuelve el drawable por nombre o un fallback.
     */
    @DrawableRes
    fun drawableOrFallback(ctx: Context, name: String, @DrawableRes fallback: Int): Int {
        val id = idFromName(ctx, name)
        return if (id != 0) id else fallback
    }
}
