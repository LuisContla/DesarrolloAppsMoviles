package com.example.gameverse.util

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

object Transitions {

    /** Disolución suave entre Fragments. */
    fun applyFadeThrough(fragment: Fragment, duration: Long = 250L) {
        fragment.enterTransition = MaterialFadeThrough().apply { this.duration = duration }
        fragment.exitTransition = MaterialFadeThrough().apply { this.duration = duration }
        fragment.reenterTransition = MaterialFadeThrough().apply { this.duration = duration }
        fragment.returnTransition = MaterialFadeThrough().apply { this.duration = duration }
    }

    /** Eje compartido (X/Y/Z) para navegaciones dentro del mismo host. */
    fun applySharedAxis(
        fragment: Fragment,
        axis: Int = MaterialSharedAxis.Z,
        forward: Boolean = true,
        duration: Long = 250L
    ) {
        fragment.enterTransition = MaterialSharedAxis(axis, forward).apply { this.duration = duration }
        fragment.returnTransition = MaterialSharedAxis(axis, !forward).apply { this.duration = duration }
        fragment.exitTransition = MaterialSharedAxis(axis, forward).apply { this.duration = duration }
        fragment.reenterTransition = MaterialSharedAxis(axis, !forward).apply { this.duration = duration }
    }

    /** Transformación de contenedor para shared element (tarjeta → detalle). */
    fun containerTransformForFragment(
        colorAnchor: View? = null,
        duration: Long = 350L
    ): MaterialContainerTransform {
        return MaterialContainerTransform().apply {
            drawingViewId = android.R.id.content
            scrimColor = Color.TRANSPARENT
            this.duration = duration
            val surface = if (colorAnchor != null)
                MaterialColors.getColor(colorAnchor, com.google.android.material.R.attr.colorSurface)
            else Color.TRANSPARENT
            setAllContainerColors(surface)
        }
    }

    /** Helper para lanzar otra Activity con shared element. */
    fun sceneTransition(
        activity: AppCompatActivity,
        sharedView: View,
        transitionName: String
    ): Bundle? {
        return ActivityOptionsCompat
            .makeSceneTransitionAnimation(activity, sharedView, transitionName)
            .toBundle() // <- devuelve Bundle?
    }
}
