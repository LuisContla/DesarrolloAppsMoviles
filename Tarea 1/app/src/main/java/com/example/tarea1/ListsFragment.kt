package com.example.tarea1 // O tu paquete

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tarea1.databinding.FragmentListsBinding // Importa tu ViewBinding generado
import com.example.tarea1.databinding.ListItemSampleBinding // ViewBinding para el item de la lista

class ListsFragment : Fragment() {

    private var _binding: FragmentListsBinding? = null
    private val binding get() = _binding!!

    private lateinit var sampleAdapter: SampleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val sampleData = List(20) { "Elemento de la lista N° ${it + 1}" }
        sampleAdapter = SampleListAdapter(sampleData)

        binding.recyclerViewSample.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = sampleAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// --- Adapter para RecyclerView ---
class SampleListAdapter(private val items: List<String>) :
    RecyclerView.Adapter<SampleListAdapter.SampleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        val binding = ListItemSampleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SampleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class SampleViewHolder(private val binding: ListItemSampleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemText: String) {
            binding.textViewItem.text = itemText
            itemView.setOnClickListener {
                // Acción al hacer clic en un item, si es necesario
                android.widget.Toast.makeText(itemView.context, "Clic en: $itemText", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }
}
