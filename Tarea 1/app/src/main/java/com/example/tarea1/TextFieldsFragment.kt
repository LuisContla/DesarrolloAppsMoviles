package com.example.tarea1 // O tu paquete

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.tarea1.databinding.FragmentTextFieldsBinding // Importa tu ViewBinding generado

class TextFieldsFragment : Fragment() {

    private var _binding: FragmentTextFieldsBinding? = null
    private val binding get() = _binding!!

    // Usar el delegate ktx para obtener el ViewModel compartido con el Activity como scope
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTextFieldsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonShowTextAndSend.setOnClickListener {
            val inputText = binding.editTextInteractive.text.toString()
            val basicText = binding.editTextBasic.text.toString()
            val emailText = binding.textInputEditTextEmail.text.toString()

            var resultMessage = "Texto Interactivo: ${if (inputText.isEmpty()) "Vacío" else inputText}\n"
            resultMessage += "Texto Básico: ${if (basicText.isEmpty()) "Vacío" else basicText}\n"
            resultMessage += "Email: ${if (emailText.isEmpty()) "Vacío" else emailText}"

            if (inputText.isEmpty() && basicText.isEmpty() && emailText.isEmpty()) {
                binding.textViewInteractiveResult.text = "Por favor, ingresa algún texto en los campos."
            } else {
                binding.textViewInteractiveResult.text = resultMessage
                // Enviar datos al ViewModel Compartido si es necesario (ej. a DisplayFragment)
                sharedViewModel.sendData(inputText, basicText, emailText)
            }
        }

        binding.textInputEditTextEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val email = binding.textInputEditTextEmail.text.toString()
                if (email.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.textInputLayoutEmail.error = "Correo electrónico no válido"
                } else {
                    binding.textInputLayoutEmail.error = null
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
