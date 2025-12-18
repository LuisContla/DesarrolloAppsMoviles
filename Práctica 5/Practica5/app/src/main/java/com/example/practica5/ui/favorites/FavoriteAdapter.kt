package com.example.practica5.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practica5.R
import com.example.practica5.data.local.entity.FavoriteEntity

class FavoriteAdapter(
    private var items: List<FavoriteEntity>,
    private val onRemove: (FavoriteEntity) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.VH>() {

    fun submit(newItems: List<FavoriteEntity>) {
        items = newItems
        notifyDataSetChanged()
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val ivPoster: ImageView = v.findViewById(R.id.ivPoster)
        val tvTitle: TextView = v.findViewById(R.id.tvTitle)
        val tvGenres: TextView = v.findViewById(R.id.tvGenres)
        val btnFav: Button = v.findViewById(R.id.btnFav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_reco_card, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val f = items[position]

        holder.tvTitle.text = f.title
        holder.tvGenres.text = f.genresCsv ?: ""

        val url = f.imageUrl
        if (url.isNullOrBlank()) {
            holder.ivPoster.visibility = View.GONE
        } else {
            holder.ivPoster.visibility = View.VISIBLE
            Glide.with(holder.itemView)
                .load(url)
                .centerCrop()
                .into(holder.ivPoster)
        }

        holder.btnFav.text = "🗑 Quitar"
        holder.btnFav.setOnClickListener { onRemove(f) }
    }

    override fun getItemCount() = items.size
}