package com.example.practica5.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practica5.R
import com.example.practica5.data.local.entity.ItemEntity

class ItemsSearchAdapter(
    private var items: List<ItemEntity>,
    private val onFav: (ItemEntity) -> Unit
) : RecyclerView.Adapter<ItemsSearchAdapter.VH>() {

    fun submit(newItems: List<ItemEntity>) {
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

        // Items no tienen imagen -> ocultamos el ImageView
        holder.ivPoster.visibility = View.GONE

        holder.tvTitle.text = item.title
        holder.tvGenres.text = "Cat: ${item.category} | Rating: ${item.rating}"

        holder.btnFav.text = "❤️ Favorito"
        holder.btnFav.setOnClickListener {
            onFav(item) // ✅ IMPORTANTE: pasar item, NO "it"
        }
    }

    override fun getItemCount() = items.size
}
