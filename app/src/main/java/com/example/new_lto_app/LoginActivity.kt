package com.example.new_lto_app

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.new_lto_app.network.ApiClient
import com.example.new_lto_app.network.ApiErrors
import com.example.new_lto_app.network.LoginRequest
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        session = SessionManager(this)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<TextView>(R.id.btnLogin)
        val loginLabel = btnLogin.text

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            if (!validate(email, password)) return@setOnClickListener

            btnLogin.isEnabled = false
            btnLogin.text = "Logging in..."

            lifecycleScope.launch {
                try {
                    // POST /api/auth/login -> token + user details
                    val auth = ApiClient.api.login(LoginRequest(email, password))
                    session.saveLogin(auth)
                    toast("Welcome, ${auth.user.fullName}!")
                    goToHome()
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    toastLong(ApiErrors.message(e))
                } finally {
                    btnLogin.isEnabled = true
                    btnLogin.text = loginLabel
                }
            }
        }

        findViewById<TextView>(R.id.btnContinueOffline).setOnClickListener {
            // Use the app without an account (hybrid design). Exams stay on the phone.
            session.continueOffline()
            goToHome()
        }

        findViewById<TextView>(R.id.tvForgotPassword).setOnClickListener {
            toast("Forgot password is not available yet")
        }

        findViewById<TextView>(R.id.tvGoToRegister).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validate(email: String, password: String): Boolean {
        if (email.isEmpty()) { toast("Please enter your email"); return false }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { toast("Please enter a valid email"); return false }
        if (password.isEmpty()) { toast("Please enter your password"); return false }
        return true
    }

    private fun goToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    private fun toastLong(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}