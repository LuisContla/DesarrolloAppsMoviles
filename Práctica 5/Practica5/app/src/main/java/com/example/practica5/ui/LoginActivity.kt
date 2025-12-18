package com.example.practica5.ui

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.practica5.R
import com.example.practica5.data.SessionStore
import com.example.practica5.data.remote.BackendClient
import com.example.practica5.data.remote.dto.backend.LoginReq
import com.example.practica5.data.remote.dto.backend.RegisterReq
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val backend by lazy { BackendClient.backendApi(this) }
    private val session by lazy { SessionStore(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Si ya hay token, pasa a Main
        lifecycleScope.launch {
            val token = session.getToken()
            if (!token.isNullOrBlank()) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }

        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPass = findViewById<EditText>(R.id.etPassword)
        val etName = findViewById<EditText>(R.id.etName)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            lifecycleScope.launch {
                try {
                    val resp = backend.login(LoginReq(etEmail.text.toString(), etPass.text.toString()))
                    session.save(resp.token, resp.user)
                    goMain()
                } catch (e: Exception) {
                    toast("Login falló: ${e.message}")
                }
            }
        }

        findViewById<Button>(R.id.btnRegisterUser).setOnClickListener {
            register(etName.text.toString(), etEmail.text.toString(), etPass.text.toString(), "USER")
        }
        findViewById<Button>(R.id.btnRegisterAdmin).setOnClickListener {
            register(etName.text.toString(), etEmail.text.toString(), etPass.text.toString(), "ADMIN")
        }
    }

    private fun register(name: String, email: String, pass: String, role: String) {
        lifecycleScope.launch {
            try {
                val resp = backend.register(RegisterReq(name, email, pass, role))
                session.save(resp.token, resp.user)
                goMain()
            } catch (e: Exception) {
                toast("Registro falló: ${e.message}")
            }
        }
    }

    private fun goMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}
