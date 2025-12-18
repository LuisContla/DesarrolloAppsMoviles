package com.example.practica5.ui.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practica5.R
import com.example.practica5.data.local.entity.ItemEntity

class ItemsAdapter(private var items: List<ItemEntity>) : RecyclerView.Adapter<ItemsAdapter.VH>() {
    fun submit(newItems: List<ItemEntity>) { items = newItems; notifyDataSetChanged() }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvTitle: TextView = v.findViewById(R.id.tvTitle)
        val tvCategory: TextView = v.findViewById(R.id.tvCategory)
        val tvRating: TextView = v.findViewById(R.id.tvRating)
        val tvDesc: TextView = v.findViewById(R.id.tvDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_item_card, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val it = items[position]
        holder.tvTitle.text = it.title
        holder.tvCategory.text = "Categoría: ${it.category}"
        holder.tvRating.text = "Rating: ${it.rating}"
        holder.tvDesc.text = it.description ?: ""
    }

    override fun getItemCount() = items.size
}
