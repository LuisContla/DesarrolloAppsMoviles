package com.example.gameverse.util

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import com.google.android.material.card.MaterialCardView
import kotlin.math.max
import kotlin.math.min

object NeonEdge {

    /**
     * Aplica un borde "neón" y un glow animado derivados del color dominante de la imagen.
     * - card: la MaterialCardView a colorear (stroke).
     * - glowView: una View debajo de la card (misma medida) donde se pinta un radial gradient.
     */
    fun applyNeonFromImage(
        context: Context,
        card: MaterialCardView,
        glowView: View,
        @DrawableRes imageRes: Int
    ) {
        // 1) Decodifica reducido para performance
        val opts = BitmapFactory.Options().apply { inSampleSize = 4 }
        val bmp = BitmapFactory.decodeResource(context.resources, imageRes, opts) ?: run {
            clearNeon(glowView)
            return
        }

        // 2) Palette asíncrono
        Palette.from(bmp).clearFilters().maximumColorCount(16).generate { palette ->
            val base = pickPaletteColor(palette)
                ?: ContextCompat.getColor(context, android.R.color.white)

            // Asegurar brillo (neón = color más "encendido")
            val neon = ensureBrightness(base, minLightness = 0.6f, boostSaturation = 0.25f)

            // 3) Borde de la card
            card.strokeColor = neon

            // 4) Glow radial sobre la glowView
            val gradient = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                gradientType = GradientDrawable.RADIAL_GRADIENT
                // del color (con alpha) a transparente
                colors = intArrayOf(ColorUtils.setAlphaComponent(neon, 140), 0x00FFFFFF)
                gradientRadius = glowView.width.coerceAtLeast(glowView.height).toFloat()
                setGradientCenter(0.5f, 0.5f)
            }
            // Si width/height aún son 0 (primer bind), post para medir:
            if (glowView.width == 0 || glowView.height == 0) {
                glowView.post {
                    gradient.gradientRadius = glowView.width.coerceAtLeast(glowView.height).toFloat()
                    glowView.background = gradient
                }
            } else {
                glowView.background = gradient
            }

            // 5) Animación: parpadeo/pulso
            startPulsing(glowView)
        }
    }

    private fun pickPaletteColor(palette: Palette?): Int? {
        if (palette == null) return null
        val candidates = listOfNotNull(
            palette.vibrantSwatch?.rgb,
            palette.lightVibrantSwatch?.rgb,
            palette.darkVibrantSwatch?.rgb,
            palette.dominantSwatch?.rgb,
            palette.mutedSwatch?.rgb
        )
        return candidates.firstOrNull()
    }

    private fun ensureBrightness(color: Int, minLightness: Float, boostSaturation: Float): Int {
        // Ajuste en HSL para asegurar "neón"
        val hsl = FloatArray(3)
        ColorUtils.colorToHSL(color, hsl)
        hsl[1] = min(1f, hsl[1] + boostSaturation)       // saturación
        hsl[2] = max(minLightness, hsl[2])               // claridad
        return ColorUtils.HSLToColor(hsl)
    }

    private fun startPulsing(glowView: View) {
        // Evita duplicar animaciones
        (glowView.getTag(glowView.id) as? ObjectAnimator)?.cancel()

        val anim = ObjectAnimator.ofFloat(glowView, View.ALPHA, 0.15f, 0.7f).apply {
            duration = 1200
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            start()
        }
        glowView.setTag(glowView.id, anim)
    }

    fun clearNeon(glowView: View) {
        (glowView.getTag(glowView.id) as? ObjectAnimator)?.cancel()
        glowView.setTag(glowView.id, null)
        glowView.alpha = 0f
        glowView.background = null
    }
}
