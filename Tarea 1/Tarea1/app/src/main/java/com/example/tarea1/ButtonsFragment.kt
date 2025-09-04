package com.example.tarea1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tarea1.databinding.FragmentButtonsBinding // Importa tu ViewBinding generado

class ButtonsFragment : Fragment() {

    private var _binding: FragmentButtonsBinding? = null
    // Esta propiedad solo es válida entre onCreateView y onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentButtonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonStandard.setOnClickListener {
            Toast.makeText(context, "Botón Estándar Presionado", Toast.LENGTH_SHORT).show()
        }

        binding.buttonOutlined.setOnClickListener {
            Toast.makeText(context, "Botón Contorneado Presionado", Toast.LENGTH_SHORT).show()
        }

        binding.buttonText.setOnClickListener {
            Toast.makeText(context, "Botón de Texto Presionado", Toast.LENGTH_SHORT).show()
        }

        binding.buttonToggle.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.buttonToggleLeft -> Toast.makeText(context, "Toggle: Izquierda", Toast.LENGTH_SHORT).show()
                    R.id.buttonToggleCenter -> Toast.makeText(context, "Toggle: Centro", Toast.LENGTH_SHORT).show()
                    R.id.buttonToggleRight -> Toast.makeText(context, "Toggle: Derecha", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.fabAdd.setOnClickListener {
            Toast.makeText(context, "Floating Action Button Presionado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Limpiar la referencia para evitar memory leaks
    }
}
