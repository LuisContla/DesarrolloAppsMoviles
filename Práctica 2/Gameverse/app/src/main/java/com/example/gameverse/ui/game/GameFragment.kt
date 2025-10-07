package com.example.gameverse.ui.game

import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.gameverse.data.GameRepository
import com.example.gameverse.data.models.Game
import com.example.gameverse.databinding.FragmentGameBinding
import com.google.android.material.chip.Chip
import com.google.android.material.transition.MaterialFadeThrough
import com.example.gameverse.util.ResExt

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private lateinit var game: Game
    private var descExpanded = false
    private val DESC_MAX_LINES = 3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        enterTransition = MaterialFadeThrough()
        exitTransition  = MaterialFadeThrough()

        val gameId = requireArguments().getString(ARG_GAME_ID)!!
        game = GameRepository.games.first { it.id == gameId }

        // Portada (prefiere drawable llamado como el id del juego; usa coverRes como fallback)
        val coverId = ResExt.drawableOrFallback(
            requireContext(),
            game.id,          // p. ej. "game_dmc5"  -> res/drawable/game_dmc5.(png|webp|jpg)
            game.coverRes
        )
        binding.imgCover.setImageResource(coverId)

        // Título
        binding.txtTitle.text = game.title

        // Año → chip
        val yearText = game.year?.toString()
        fillChipGroup(
            label = binding.labelYear,
            group = binding.chipsYear,
            items = if (yearText.isNullOrBlank()) emptyList() else listOf(yearText)
        )

        // Publicador → chip
        fillChipGroup(
            label = binding.labelPublisher,
            group = binding.chipsPublisher,
            items = if (game.publisher.isNullOrBlank()) emptyList() else listOf(game.publisher!!)
        )

        // Desarrolladores → chips
        fillChipGroup(
            label = binding.labelDevelopers,
            group = binding.chipsDevelopers,
            items = game.developers
        )

        // Plataformas → chips
        fillChipGroup(
            label = binding.labelPlatforms,
            group = binding.chipsPlatforms,
            items = game.platforms
        )

        // Descripción
        binding.txtDescription.text = game.description ?: "—"
        setupDescriptionToggle()

        // Facts (bullets) — oculta card si está vacío
        renderFacts(game.facts)
    }

    private fun setupDescriptionToggle() {
        val tv = binding.txtDescription

        // Asegura estado colapsado inicial
        collapseDescription(applyChange = true)

        // Checa si necesita "Leer más" (hay elipsis)
        tv.post {
            val needsMore = needsEllipsis(tv, DESC_MAX_LINES)
            binding.btnToggleDesc.isVisible = needsMore
        }

        binding.btnToggleDesc.setOnClickListener {
            if (descExpanded) collapseDescription() else expandDescription()
        }
    }

    private fun needsEllipsis(tv: TextView, maxLines: Int): Boolean {
        val layout = tv.layout ?: return false
        return layout.lineCount > maxLines ||
                (layout.lineCount >= maxLines && layout.getEllipsisCount(maxLines - 1) > 0)
    }

    private fun expandDescription() {
        val tv = binding.txtDescription
        tv.maxLines = Int.MAX_VALUE
        tv.ellipsize = null
        binding.btnToggleDesc.text = "Leer menos"
        descExpanded = true
    }

    private fun collapseDescription(applyChange: Boolean = false) {
        val tv = binding.txtDescription
        if (applyChange) {
            tv.maxLines = DESC_MAX_LINES
            tv.ellipsize = TextUtils.TruncateAt.END
        }
        binding.btnToggleDesc.text = "Leer más"
        descExpanded = false
    }

    private fun fillChipGroup(label: View, group: com.google.android.material.chip.ChipGroup, items: List<String>) {
        group.removeAllViews()
        val has = items.isNotEmpty()
        label.isVisible = has
        group.isVisible = has
        if (!has) return
        items.forEach { txt -> group.addView(makeChip(txt)) }
    }

    private fun makeChip(text: String): Chip {
        return Chip(requireContext()).apply {
            this.text = text
            isCheckable = false
            isClickable = false
        }
    }

    private fun renderFacts(facts: List<String>) {
        val container = binding.linearFacts
        container.removeAllViews()
        val hasFacts = facts.isNotEmpty()
        binding.cardFacts.isVisible = hasFacts
        binding.labelFacts.isVisible = hasFacts
        if (!hasFacts) return
        facts.forEach { container.addView(bullet(it)) }
    }

    private fun bullet(text: String): View {
        return TextView(requireContext()).apply {
            this.text = "• $text"
            textSize = 14f
            setPadding(0, 6, 0, 6)
            gravity = Gravity.START
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_GAME_ID = "gameId"
        fun newInstance(gameId: String) = GameFragment().apply {
            arguments = bundleOf(ARG_GAME_ID to gameId)
        }
    }
}
