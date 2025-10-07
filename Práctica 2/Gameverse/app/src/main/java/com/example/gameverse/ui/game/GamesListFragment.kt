package com.example.gameverse.ui.game

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gameverse.R
import com.example.gameverse.data.GameRepository
import com.example.gameverse.databinding.FragmentGamesListBinding
import com.example.gameverse.util.GridSpacingItemDecoration
import com.google.android.material.transition.MaterialFadeThrough
import com.example.gameverse.util.ResExt

class GamesListFragment : Fragment() {

    private var _binding: FragmentGamesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var franchiseId: String
    private lateinit var adapter: GameListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        franchiseId = requireArguments().getString(ARG_FRANCHISE_ID) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()

        // Adapter: callback de 1 parámetro (GameRow)
        adapter = GameListAdapter { row ->
            val intent = Intent(requireContext(), GameActivity::class.java).apply {
                putExtra(GameActivity.EXTRA_GAME_ID, row.gameId)
                putExtra(GameActivity.EXTRA_GAME_TITLE, row.title)
            }
            startActivity(intent)
        }


        // Grid (1 columna como pediste; cambia a 2 si prefieres)
        val span = 1
        binding.rvGames.layoutManager = GridLayoutManager(requireContext(), span)
        binding.rvGames.adapter = adapter
        if (binding.rvGames.itemDecorationCount == 0) {
            val spacing = resources.getDimensionPixelSize(R.dimen.grid_spacing)
            binding.rvGames.addItemDecoration(GridSpacingItemDecoration(span, spacing))
        }

        // Mapea Game -> GameRow (lo que esperaba el adapter)
        val rows = GameRepository.games
            .filter { it.franchiseId == franchiseId }
            .map { g ->
                val cover = ResExt.drawableOrFallback(
                    requireContext(),
                    g.id,                 // ← usa el id como nombre de archivo
                    g.coverRes            // ← fallback: lo que ya traías en el repo
                )
                GameRow(gameId = g.id, title = g.title, coverRes = cover)
            }

        adapter.submitList(rows)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_FRANCHISE_ID = "franchiseId"
        fun newInstance(franchiseId: String) = GamesListFragment().apply {
            arguments = bundleOf(ARG_FRANCHISE_ID to franchiseId)
        }
    }
}
