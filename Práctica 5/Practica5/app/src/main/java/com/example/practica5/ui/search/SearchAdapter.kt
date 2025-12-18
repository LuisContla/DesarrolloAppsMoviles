package com.example.practica5.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practica5.R
import com.example.practica5.data.local.entity.TvShowEntity

class SearchAdapter(
    private var items: List<TvShowEntity>,
    private val onFav: (TvShowEntity) -> Unit
) : RecyclerView.Adapter<SearchAdapter.VH>() {

    fun submit(newItems: List<TvShowEntity>) {
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
        val item = items[position]
        holder.tvTitle.text = item.name
        holder.tvGenres.text = item.genresCsv ?: ""

        val url = item.imageUrl
        if (url.isNullOrBlank()) {
            holder.ivPoster.visibility = View.GONE
        } else {
            holder.ivPoster.visibility = View.VISIBLE
            Glide.with(holder.itemView)
                .load(url)
                .centerCrop()
                .into(holder.ivPoster)
        }

        holder.btnFav.text = "❤️ Favorito"
        holder.btnFav.setOnClickListener { onFav(item) }
    }

    override fun getItemCount() = items.size
}