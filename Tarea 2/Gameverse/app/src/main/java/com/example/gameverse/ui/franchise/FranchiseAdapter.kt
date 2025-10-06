package com.example.gameverse.ui.franchise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gameverse.databinding.ItemFranchiseCardBinding

data class FranchiseRow(
    val franchiseId: String,
    val franchiseName: String,
    val artRes: Int
)

class FranchiseAdapter(
    private val onCardClick: (FranchiseRow) -> Unit
) : ListAdapter<FranchiseRow, FranchiseAdapter.VH>(Diff()) {

    inner class VH(val b: ItemFranchiseCardBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemFranchiseCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        with(holder.b) {
            txtTitle.text = item.franchiseName
            imgArt.setImageResource(item.artRes)
            // opcional: útil si luego quieres transición compartida
            card.transitionName = "franchise_${item.franchiseId}"

            card.setOnClickListener { onCardClick(item) }
        }
    }

    class Diff : DiffUtil.ItemCallback<FranchiseRow>() {
        override fun areItemsTheSame(oldItem: FranchiseRow, newItem: FranchiseRow) =
            oldItem.franchiseId == newItem.franchiseId

        override fun areContentsTheSame(oldItem: FranchiseRow, newItem: FranchiseRow) =
            oldItem == newItem
    }
}
