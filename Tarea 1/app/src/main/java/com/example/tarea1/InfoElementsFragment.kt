package com.example.tarea1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tarea1.databinding.FragmentInfoElementsBinding // Importa tu ViewBinding generado

class InfoElementsFragment : Fragment() {

    private var _binding: FragmentInfoElementsBinding? = null
    private val binding get() = _binding!!

    private var progressStatus = 0
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoElementsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewInfoTitle.text = "Elementos Informativos"
        binding.textViewInfoDescription.text = "Esto es una descripción de ejemplo para mostrar cómo se puede usar un TextView para información más detallada. Los TextViews son versátiles para mostrar texto estático o dinámico."

        // Ejemplo de ProgressBar
        binding.buttonStartProgress.setOnClickListener {
            startProgressSimulation()
        }

        // Cargar una imagen de ejemplo (asegúrate de tener una imagen en res/drawable)
        // Por ejemplo, ic_android_green.xml (Vector Asset)
        // Si no tienes una, créala: File > New > Vector Asset, busca "android"
        binding.imageViewInfo.setImageResource(R.drawable.ic_android_green) // Reemplaza con tu imagen
    }

    private fun startProgressSimulation() {
        progressStatus = 0
        binding.progressBarHorizontal.progress = 0
        binding.progressBarCircular.visibility = View.VISIBLE
        binding.textViewProgress.text = "Cargando... 0%"

        Thread {
            while (progressStatus < 100) {
                progressStatus += 5
                handler.post {
                    binding.progressBarHorizontal.progress = progressStatus
                    binding.textViewProgress.text = "Cargando... $progressStatus%"
                    if (progressStatus >= 100) {
                        binding.progressBarCircular.visibility = View.GONE
                        binding.textViewProgress.text = "Carga Completa"
                    }
                }
                try {
                    Thread.sleep(200)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null) // Detener cualquier post del handler
        _binding = null
    }
}
