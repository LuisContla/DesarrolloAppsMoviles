package com.example.gameverse.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gameverse.data.GameRepository
import com.example.gameverse.data.models.Genre
import com.example.gameverse.databinding.FragmentGenresBinding
import com.example.gameverse.ui.franchise.FranchiseActivity

class GenresFragment : Fragment() {

    private var _binding: FragmentGenresBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GenreAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = GenreAdapter { genre: Genre ->
            openFranchises(genre)
        }

        // si quieres 1 columna: spanCount = 1; para 2 columnas: 2
        binding.rvGenres.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.rvGenres.adapter = adapter

        adapter.submitList(GameRepository.genres)
    }

    // Dentro de GenresFragment.kt
    private fun openFranchises(genre: Genre) {
        val intent = Intent(requireContext(), FranchiseActivity::class.java).apply {
            putExtra(FranchiseActivity.EXTRA_GENRE_ID, genre.id)
            putExtra(FranchiseActivity.EXTRA_GENRE_NAME, genre.name)
        }
        startActivity(intent)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
