package com.example.gameverse.ui.franchise

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
import com.example.gameverse.databinding.FragmentFranchiseBinding
import com.example.gameverse.util.GridSpacingItemDecoration
import com.google.android.material.transition.MaterialFadeThrough
import com.example.gameverse.ui.game.GamesActivity

class FranchiseFragment : Fragment() {

    private var _binding: FragmentFranchiseBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FranchiseAdapter
    private var genreId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreId = requireArguments().getString(ARG_GENRE_ID).orEmpty()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFranchiseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        enterTransition = MaterialFadeThrough()
        exitTransition  = MaterialFadeThrough()

        // Dentro de FranchiseFragment.kt → onViewCreated()
        adapter = FranchiseAdapter { row ->
            val intent = Intent(requireContext(), GamesActivity::class.java).apply {
                putExtra(GamesActivity.EXTRA_FRANCHISE_ID, row.franchiseId)
                putExtra(GamesActivity.EXTRA_FRANCHISE_NAME, row.franchiseName)
            }
            startActivity(intent)
        }


        val span = 2
        binding.rvFranchise.layoutManager = GridLayoutManager(requireContext(), span)
        if (binding.rvFranchise.itemDecorationCount == 0) {
            val spacing = resources.getDimensionPixelSize(R.dimen.grid_spacing)
            binding.rvFranchise.addItemDecoration(GridSpacingItemDecoration(span, spacing))
        }
        binding.rvFranchise.adapter = adapter

        // 🔎 Si genreId está vacío, muestra TODAS las franquicias
        val list = if (genreId.isBlank()) {
            GameRepository.franchises
        } else {
            GameRepository.franchises.filter { it.genreId == genreId }
        }

        val rows = list.map { f -> FranchiseRow(f.id, f.name, f.artRes) }
        adapter.submitList(rows)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_GENRE_ID = "genreId"
        fun newInstance(genreId: String) = FranchiseFragment().apply {
            arguments = bundleOf(ARG_GENRE_ID to genreId)
        }
    }
}
