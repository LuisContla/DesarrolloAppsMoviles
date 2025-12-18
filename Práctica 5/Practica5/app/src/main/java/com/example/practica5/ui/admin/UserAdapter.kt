package com.example.practica5.ui.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practica5.R
import com.example.practica5.data.remote.dto.backend.UserDto
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserAdapter(
    private var items: List<UserDto>
) : RecyclerView.Adapter<UserAdapter.VH>() {

    fun submit(newItems: List<UserDto>) {
        items = newItems
        notifyDataSetChanged()
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvName: TextView = v.findViewById(R.id.tvUserName)
        val tvEmail: TextView = v.findViewById(R.id.tvUserEmail)
        val tvRole: TextView = v.findViewById(R.id.tvUserRole)
        val tvId: TextView = v.findViewById(R.id.tvUserId)
        val tvCreated: TextView = v.findViewById(R.id.tvUserCreated)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_user_card, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val u = items[position]
        holder.tvName.text = u.name
        holder.tvEmail.text = "Correo: ${u.email}"
        holder.tvRole.text = "Rol: ${u.role}"
        holder.tvId.text = "ID: ${u.id}"
        holder.tvCreated.text = "Creado: (no disponible en UserDto)"
        // Si luego quieres created_at, lo añadimos al DTO y lo mostramos formateado
    }

    override fun getItemCount(): Int = items.size
}
