package com.example.new_lto_app

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.new_lto_app.network.ApiClient
import com.example.new_lto_app.network.ApiErrors
import com.example.new_lto_app.network.RegisterRequest
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val session = SessionManager(this)

        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnRegister = findViewById<TextView>(R.id.btnRegister)
        val registerLabel = btnRegister.text

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        btnRegister.setOnClickListener {
            val fullName = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            if (!validate(fullName, email, password, confirmPassword)) return@setOnClickListener

            btnRegister.isEnabled = false
            btnRegister.text = "Creating account..."

            lifecycleScope.launch {
                try {
                    // POST /api/auth/register -> creates the account and logs in right away
                    val auth = ApiClient.api.register(
                        RegisterRequest(fullName, email, password, targetLicense = "NON_PROF")
                    )
                    session.saveLogin(auth)
                    toast("Account created. Welcome, ${auth.user.fullName}!")
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    toastLong(ApiErrors.message(e))
                } finally {
                    btnRegister.isEnabled = true
                    btnRegister.text = registerLabel
                }
            }
        }

        findViewById<TextView>(R.id.tvGoToLogin).setOnClickListener { finish() }
    }

    private fun validate(fullName: String, email: String, password: String, confirmPassword: String): Boolean {
        if (fullName.isEmpty()) { toast("Please enter your full name"); return false }
        if (email.isEmpty()) { toast("Please enter your email"); return false }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { toast("Please enter a valid email"); return false }
        if (password.isEmpty()) { toast("Please enter a password"); return false }
        if (password.length < 8) { toast("Password must be at least 8 characters"); return false }
        if (password != confirmPassword) { toast("Passwords do not match"); return false }
        return true
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    private fun toastLong(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}