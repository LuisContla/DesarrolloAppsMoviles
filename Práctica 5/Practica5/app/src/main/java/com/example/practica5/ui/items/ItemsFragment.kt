package com.example.practica5.ui.items

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica5.R
import com.example.practica5.data.AppRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController

class ItemsFragment : Fragment(R.layout.fragment_items) {

    private val repo by lazy { AppRepository(requireContext()) }
    private var currentSort: String = "updated_desc"
    private var lastList: List<com.example.practica5.data.local.entity.ItemEntity> = emptyList()

    private fun applySort(list: List<com.example.practica5.data.local.entity.ItemEntity>): List<com.example.practica5.data.local.entity.ItemEntity> {
        return when (currentSort) {
            "rating_desc" -> list.sortedByDescending { it.rating }
            "title_asc" -> list.sortedBy { it.title.lowercase() }
            else -> list.sortedByDescending { it.updatedAt } // updated_desc
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tvStatus = view.findViewById<TextView>(R.id.tvItemsStatus)
        val etQ = view.findViewById<EditText>(R.id.etQ)
        val etCategory = view.findViewById<EditText>(R.id.etCategory)
        val etMinRating = view.findViewById<EditText>(R.id.etMinRating)
        val spSort = view.findViewById<Spinner>(R.id.spSort)
        val btn = view.findViewById<Button>(R.id.btnItemsRefresh)
        val rv = view.findViewById<RecyclerView>(R.id.rvItems)
        val adapter = ItemsAdapter(emptyList())
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        // Spinner opciones
        val sortLabels = listOf("Más recientes", "Mejor rating", "Título A-Z")
        val sortValues = listOf("updated_desc", "rating_desc", "title_asc")

        lifecycleScope.launch {
            if (!repo.isAdmin()) {
                findNavController().navigate(R.id.homeFragment)
                return@launch
            }
        }

        spSort.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, sortLabels)
        spSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val sortValues = listOf("updated_desc", "rating_desc", "title_asc")
                currentSort = sortValues[position]

                // Reordena al instante lo que ya está en pantalla (sin tocar filtros / backend)
                val sorted = applySort(lastList)
                adapter.submit(sorted)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        // Offline-first: siempre muestra lo local
        lifecycleScope.launch {
            repo.itemsFlow().collectLatest { list ->
                lastList = list
                val sorted = applySort(list)
                adapter.submit(sorted)
                tvStatus.text = "Local: ${sorted.size} items"
            }
        }

        btn.setOnClickListener {
            val q = etQ.text.toString().trim().ifBlank { null }
            val cat = etCategory.text.toString().trim().ifBlank { null }
            val minRating = etMinRating.text.toString().trim()
                .takeIf { it.isNotBlank() }
                ?.toDoubleOrNull()

            currentSort = sortValues[spSort.selectedItemPosition]

            lifecycleScope.launch {
                tvStatus.text = "Buscando en backend..."
                runCatching { repo.refreshItems(q = q, category = cat, minRating = minRating, sort = currentSort) }
                    .onSuccess { tvStatus.text = "Resultados guardados ✅ (solo coincidencias)" }
                    .onFailure { tvStatus.text = "Error/red: ${it.message}" }
            }

        }
    }
}
