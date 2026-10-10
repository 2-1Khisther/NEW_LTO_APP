package com.example.new_lto_app


import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        findViewById<TextView>(R.id.btnLogin).setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            if (!validate(email, password)) return@setOnClickListener

            // TODO: replace this with a real call to your backend API, e.g.
            //   POST /login { email, password }
            // On success: save the returned auth token (EncryptedSharedPreferences)
            // and navigate to MainActivity. On failure: show the server's error
            // message instead of this toast.
            toast("Logging in...")
            goToHome()
        }

        findViewById<TextView>(R.id.btnContinueOffline).setOnClickListener {
            // Lets the user use the app without an account, per your hybrid
            // offline/online design — exam history just won't sync until they log in.
            goToHome()
        }

        findViewById<TextView>(R.id.tvForgotPassword).setOnClickListener {
            // TODO: build a Forgot Password screen/flow once the backend exists
            toast("Forgot password")
        }

        findViewById<TextView>(R.id.tvGoToRegister).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validate(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            toast("Please enter your email")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            toast("Please enter a valid email")
            return false
        }
        if (password.isEmpty()) {
            toast("Please enter your password")
            return false
        }
        return true
    }

    private fun goToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}