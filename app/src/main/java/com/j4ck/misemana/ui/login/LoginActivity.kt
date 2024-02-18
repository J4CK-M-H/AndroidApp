package com.j4ck.misemana.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.j4ck.misemana.R
import com.j4ck.misemana.data.remote.ApiInterface
import com.j4ck.misemana.data.remote.AppApi
import com.j4ck.misemana.data.remote.request.LoginRequest
import com.j4ck.misemana.data.remote.response.LoginResponse
import com.j4ck.misemana.data.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var loader: CircularProgressIndicator
    private lateinit var apiService: ApiInterface
    private lateinit var repository: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        imageView = findViewById(R.id.imageView)
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        loader = findViewById(R.id.loader)

        apiService = AppApi.createService()
        repository = LoginRepository(apiService)

        loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (username.isNotEmpty() && password.isNotEmpty()) {
            showLoader()

            // Inicio una corrutina en el hilo Main (por defecto)
            lifecycleScope.launch {
                // Indicamos que el repositorio se ejecute en el hilo Input Output
                val result = withContext(Dispatchers.IO) { repository.login(username, password) }

                result.onSuccess { loginResponse ->
                    Log.d("JACK","response: $loginResponse")
                    hideLoader()
                    Toast.makeText(this@LoginActivity, "Bienvenido", Toast.LENGTH_SHORT).show()
                }.onFailure { exception ->
                    if (exception is IllegalAccessException) {
                        Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    } else {
                        exception.printStackTrace()
                        Toast.makeText(this@LoginActivity, "Ocurrió un error", Toast.LENGTH_SHORT).show()
                    }
                    hideLoader()
                }
            }
        } else {
            Toast.makeText(this, "Asegúrate de completar los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoader() {
        loginButton.isEnabled = false
        loader.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        loginButton.isEnabled = true
        loader.visibility = View.GONE
    }
}