package com.example.practica5.ui.search

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica5.R
import com.example.practica5.data.AppRepository
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search2) {

    private val repo by lazy { AppRepository(requireContext()) }

    private lateinit var tvMazeAdapter: SearchAdapter
    private lateinit var itemsAdapter: ItemsSearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val etQuery = view.findViewById<EditText>(R.id.etQuery)
        val btnSearch = view.findViewById<Button>(R.id.btnSearch)
        val tvStatus = view.findViewById<TextView>(R.id.tvStatus)
        val rv = view.findViewById<RecyclerView>(R.id.rvResults)

        // Selector de fuente
        val rgSource = view.findViewById<RadioGroup>(R.id.rgSource)
        val rbTvMaze = view.findViewById<RadioButton>(R.id.rbTvMaze)
        val rbItems = view.findViewById<RadioButton>(R.id.rbItems)

        // Adapter TVMaze
        tvMazeAdapter = SearchAdapter(emptyList()) { show ->
            lifecycleScope.launch {
                repo.addFavoriteFromShow(show)
                tvStatus.text = "Agregado a favoritos ✅"
            }
        }

        // Adapter Items (Mi API)
        itemsAdapter = ItemsSearchAdapter(emptyList()) { item ->
            lifecycleScope.launch {
                repo.addFavoriteFromItem(item)
                tvStatus.text = "Agregado a favoritos ✅"
            }
        }

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = tvMazeAdapter // default

        // Cambiar adapter al seleccionar la fuente
        rgSource.setOnCheckedChangeListener { _, checkedId ->
            rv.adapter = if (checkedId == R.id.rbItems) itemsAdapter else tvMazeAdapter
        }

        btnSearch.setOnClickListener {
            val q = etQuery.text.toString().trim()
            if (q.isBlank()) {
                tvStatus.text = "Escribe algo para buscar"
                return@setOnClickListener
            }

            tvStatus.text = "Buscando..."

            lifecycleScope.launch {
                if (rbItems.isChecked) {
                    // Buscar en Mi API (Items)
                    runCatching { repo.searchItems(q) }
                        .onSuccess { list ->
                            itemsAdapter.submit(list)
                            tvStatus.text = "Items: ${list.size}"
                        }
                        .onFailure { e ->
                            tvStatus.text = "Error Items: ${e.message}"
                        }
                } else {
                    // Buscar en TVMaze
                    runCatching { repo.searchTvMaze(q) }
                        .onSuccess { list ->
                            tvMazeAdapter.submit(list)
                            tvStatus.text = "TVMaze: ${list.size}"
                        }
                        .onFailure { e ->
                            tvStatus.text = "Error TVMaze: ${e.message}"
                        }
                }
            }
        }
    }
}
