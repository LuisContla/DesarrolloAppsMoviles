package com.example.practica5.ui.reco

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica5.R
import com.example.practica5.data.AppRepository
import kotlinx.coroutines.launch

class RecoFragment : Fragment(R.layout.fragment_reco2) {

    private val repo by lazy { AppRepository(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rv = view.findViewById<RecyclerView>(R.id.rvReco)

        val adapter = RecoAdapter(emptyList()) { show ->
            lifecycleScope.launch { repo.addFavoriteFromShow(show) }
        }

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        lifecycleScope.launch {
            val recos = repo.recommendations()
            adapter.submit(recos)
        }
    }
}
