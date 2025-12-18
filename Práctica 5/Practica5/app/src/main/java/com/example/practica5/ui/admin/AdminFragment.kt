package com.example.practica5.ui.admin

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica5.R
import com.example.practica5.data.AppRepository
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController

class AdminFragment : Fragment(R.layout.fragment_admin2) {

    private val repo by lazy { AppRepository(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tvStatus = view.findViewById<TextView>(R.id.tvAdminStatus)
        val rv = view.findViewById<RecyclerView>(R.id.rvUsers)

        val adapter = UserAdapter(emptyList())
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        lifecycleScope.launch {
            if (!repo.isAdmin()) {
                findNavController().navigate(R.id.homeFragment)
                return@launch
            }

            tvStatus.text = "Cargando usuarios..."
            runCatching { repo.getAdminUsers() }
                .onSuccess {
                    adapter.submit(it)
                    tvStatus.text = "Usuarios: ${it.size}"
                }
                .onFailure {
                    tvStatus.text = "Error: ${it.message}"
                }
        }
    }
}

