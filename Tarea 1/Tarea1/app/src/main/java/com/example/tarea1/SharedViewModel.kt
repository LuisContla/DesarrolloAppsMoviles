package com.example.tarea1 // Asegúrate que este sea tu nombre de paquete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// (Opcional, pero buena práctica si los datos son complejos)
// Define una clase de datos para encapsular la información
data class UserInputData(
    val interactiveText: String,
    val basicText: String,
    val emailText: String
)

class SharedViewModel : ViewModel() {

    // MutableLiveData: Estos son los datos que pueden cambiar desde dentro del ViewModel.
    // Son privados para asegurar que solo el ViewModel los modifique.
    private val _userInput = MutableLiveData<UserInputData>()

    // LiveData: Esta es la versión pública e inmutable de los datos.
    // Los fragmentos observarán este LiveData para reaccionar a los cambios.
    val userInput: LiveData<UserInputData> = _userInput

    // Función para actualizar los datos desde fuera del ViewModel (ej. desde un Fragment)
    fun sendData(interactive: String, basic: String, email: String) {
        val newData = UserInputData(interactive, basic, email)
        _userInput.value = newData // Actualiza el valor del MutableLiveData
    }

    // Ejemplo de otro LiveData si solo quisieras exponer una parte específica
    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> = _statusMessage

    fun setStatusMessage(message: String) {
        _statusMessage.value = message
    }
}
