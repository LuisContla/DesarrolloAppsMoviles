package com.example.practica5.ui.favorites

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica5.R
import com.example.practica5.data.AppRepository
import com.example.practica5.data.SessionStore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val repo by lazy { AppRepository(requireContext()) }
    private val session by lazy { SessionStore(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tvStatus = view.findViewById<TextView>(R.id.tvFavStatus)
        val rv = view.findViewById<RecyclerView>(R.id.rvFavs)

        val adapter = FavoriteAdapter(emptyList()) { fav ->
            lifecycleScope.launch {
                repo.removeFavorite(fav)
            }
        }

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        lifecycleScope.launch {
            val userId = session.getUserId()
            if (userId == null) {
                tvStatus.text = "No hay sesión."
                return@launch
            }

            repo.favoritesFlow(userId).collectLatest { favs ->
                adapter.submit(favs)
                tvStatus.text = "Favoritos: ${favs.size}"
            }
        }
    }
}