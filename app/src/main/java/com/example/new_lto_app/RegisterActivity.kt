package com.example.new_lto_app


import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        findViewById<TextView>(R.id.btnRegister).setOnClickListener {
            val fullName = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (!validate(fullName, email, password, confirmPassword)) return@setOnClickListener

            // TODO: replace this with a real call to your backend API, e.g.
            //   POST /register { name, email, password }
            // On success: either log the user in immediately, or send them back
            // to Login with a "Account created, please log in" message.
            // On failure (e.g. email already registered): show the server's
            // actual error message instead of this toast.
            toast("Account created")
            finish() // back to Login
        }

        findViewById<TextView>(R.id.tvGoToLogin).setOnClickListener {
            finish() // back to Login
        }
    }

    private fun validate(fullName: String, email: String, password: String, confirmPassword: String): Boolean {
        if (fullName.isEmpty()) {
            toast("Please enter your full name")
            return false
        }
        if (email.isEmpty()) {
            toast("Please enter your email")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            toast("Please enter a valid email")
            return false
        }
        if (password.isEmpty()) {
            toast("Please enter a password")
            return false
        }
        if (password.length < 6) {
            toast("Password must be at least 6 characters")
            return false
        }
        if (password != confirmPassword) {
            toast("Passwords do not match")
            return false
        }
        return true
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}