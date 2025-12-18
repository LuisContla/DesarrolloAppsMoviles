package com.example.practica5.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.practica5.R
import com.example.practica5.data.AppRepository
import com.example.practica5.ui.LoginActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home2) {

    private val repo by lazy { AppRepository(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tvSession = view.findViewById<TextView>(R.id.tvSession)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        val btnSync = view.findViewById<Button>(R.id.btnSync)

        lifecycleScope.launch { tvSession.text = repo.getSessionText() }

        btnSync.setOnClickListener {
            lifecycleScope.launch {
                repo.syncPending()
                tvSession.text = repo.getSessionText() + " (sync OK)"
            }
        }

        btnLogout.setOnClickListener {
            lifecycleScope.launch {
                repo.logout()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }
    }
}
