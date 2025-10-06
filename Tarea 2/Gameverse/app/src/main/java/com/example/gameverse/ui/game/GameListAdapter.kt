package com.example.gameverse.ui.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gameverse.databinding.ItemGameCardBinding

data class GameRow(
    val gameId: String,
    val title: String,
    val coverRes: Int
)

class GameListAdapter(
    private val onClick: (GameRow) -> Unit
) : ListAdapter<GameRow, GameListAdapter.VH>(Diff()) {

    inner class VH(val b: ItemGameCardBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemGameCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        with(holder.b) {
            txtTitle.text = item.title
            imgArt.setImageResource(item.coverRes)     // <- antes era imgCover
            card.transitionName = "game_${item.gameId}"
            card.setOnClickListener { onClick(item) }
        }
    }

    class Diff : DiffUtil.ItemCallback<GameRow>() {
        override fun areItemsTheSame(oldItem: GameRow, newItem: GameRow) =
            oldItem.gameId == newItem.gameId
        override fun areContentsTheSame(oldItem: GameRow, newItem: GameRow) =
            oldItem == newItem
    }
}
