package com.example.tarea1 // O tu paquete

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.tarea1.databinding.FragmentSelectionControlsBinding // Importa tu ViewBinding generado

class SelectionControlsFragment : Fragment() {

    private var _binding: FragmentSelectionControlsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectionControlsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // CheckBox
        binding.checkboxOption1.setOnCheckedChangeListener { _, isChecked ->
            showToast("Checkbox Opción 1: ${if (isChecked) "Seleccionado" else "Deseleccionado"}")
        }
        binding.checkboxOption2.setOnCheckedChangeListener { _, isChecked ->
            showToast("Checkbox Opción 2: ${if (isChecked) "Seleccionado" else "Deseleccionado"}")
        }

        // RadioGroup
        binding.radioGroupOptions.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButtonA -> showToast("Radio: Opción A seleccionada")
                R.id.radioButtonB -> showToast("Radio: Opción B seleccionada")
                R.id.radioButtonC -> showToast("Radio: Opción C seleccionada")
            }
        }

        // Switch
        binding.switchEnableFeature.setOnCheckedChangeListener { _, isChecked ->
            showToast("Switch Funcionalidad: ${if (isChecked) "Activada" else "Desactivada"}")
            binding.textViewSwitchStatus.text = if (isChecked) "Funcionalidad Activada" else "Funcionalidad Desactivada"
        }

        // Spinner
        val spinnerItems = listOf("Selecciona una fruta", "Manzana", "Banana", "Naranja", "Uva")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerItems)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFruit.adapter = spinnerAdapter

        binding.spinnerFruit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position > 0) { // Evitar el Toast para el item "Selecciona una fruta"
                    val selectedFruit = parent?.getItemAtPosition(position).toString()
                    showToast("Spinner: $selectedFruit seleccionada")
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No es necesario para este ejemplo
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
