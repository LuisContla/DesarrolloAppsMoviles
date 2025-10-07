package com.example.gameverse.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gameverse.data.models.Genre
import com.example.gameverse.databinding.ItemGenreCardBinding
import com.example.gameverse.util.IconResolver

class GenreAdapter(
    private val onGenreClick: (genre: Genre) -> Unit
) : ListAdapter<Genre, GenreAdapter.VH>(Diff()) {

    inner class VH(val b: ItemGenreCardBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemGenreCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        with(holder.b) {
            txtTitle.text = item.name

            // Resolver icono por nombre con múltiples patrones; fallback al bgRes
            val iconRes = IconResolver.resolveGenreIcon(
                root.context,
                item.id,          // id real (puede venir como g_action, etc.)
                item.name,        // “Acción”, “RPG”, ...
                item.bgRes        // fallback si no encuentra icono
            )
            imgArt.apply {
                setImageResource(iconRes)
                scaleType = android.widget.ImageView.ScaleType.CENTER_INSIDE
                adjustViewBounds = true
                val pad = (8 * resources.displayMetrics.density).toInt()
                setPadding(pad, pad, pad, pad)
            }

            // Aplica borde/glow tomando color dominante del icono
            com.example.gameverse.util.NeonEdge.applyNeonFromImage(
                context = root.context,
                card = card,
                glowView = neonGlow,
                imageRes = iconRes
            )

            card.setOnClickListener { onGenreClick(item) }
        }
    }

    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        com.example.gameverse.util.NeonEdge.clearNeon(holder.b.neonGlow)
    }

    class Diff : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Genre, newItem: Genre) = oldItem == newItem
    }
}
