package com.example.actividad1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity // IMPORTANTE: Cambiamos a FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.example.actividad1.data.datastore.PrefsRepository
import com.example.actividad1.ui.navigation.AppNavGraph
import com.example.actividad1.ui.theme.AppTheme
import java.util.concurrent.Executor

// Heredamos de FragmentActivity para soportar BiometricPrompt
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = PrefsRepository(applicationContext)

        setContent {
            AppTheme(prefs = prefs) {
                // Estado para controlar si mostramos la app o el bloqueo
                var isAuthenticated by remember { mutableStateOf(false) }

                // Intentamos autenticar apenas inicia la app
                LaunchedEffect(Unit) {
                    authenticateUser { success ->
                        isAuthenticated = success
                    }
                }

                if (isAuthenticated) {
                    // Si pasó la huella, mostramos tu AppNavGraph original
                    val nav = rememberNavController()
                    AppNavGraph(nav = nav)
                } else {
                    // Pantalla de bloqueo por si cancelan o falla la huella
                    LockScreen(
                        onRetry = {
                            authenticateUser { success -> isAuthenticated = success }
                        }
                    )
                }
            }
        }
    }

    private fun authenticateUser(onResult: (Boolean) -> Unit) {
        val executor: Executor = ContextCompat.getMainExecutor(this)

        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    // Si el usuario cancela o hay error crítico
                    Toast.makeText(applicationContext, "Error: $errString", Toast.LENGTH_SHORT).show()
                    onResult(false)
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    // ¡ÉXITO!
                    onResult(true)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    // Huella no reconocida (puede intentar de nuevo)
                    Toast.makeText(applicationContext, "Huella no reconocida", Toast.LENGTH_SHORT).show()
                }
            })

        // Configuración del diálogo
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Acceso al Gestor de Archivos")
            .setSubtitle("Usa tu huella o patrón para entrar")
            // Permite usar PIN/Patrón si no hay huella
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}

// Una pantalla simple para cuando está bloqueado
@Composable
fun LockScreen(onRetry: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Bloqueado",
                modifier = Modifier.height(64.dp).padding(bottom = 16.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Aplicación Bloqueada",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Se requiere autenticación para ver tus archivos.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onRetry) {
                Text("Desbloquear")
            }
        }
    }
}