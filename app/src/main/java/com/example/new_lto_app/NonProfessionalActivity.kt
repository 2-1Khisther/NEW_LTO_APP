package com.example.new_lto_app

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class NonProfessionalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_non_professional)

        // Back arrow closes this screen and returns to the Home Page
        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        findViewById<android.widget.Button>(R.id.btnStartMockExam).setOnClickListener {
            startActivity(Intent(this, MockExamActivity::class.java))
        }

        findViewById<android.widget.Button>(R.id.btnStartReview).setOnClickListener {
            startActivity(Intent(this, ReviewActivity::class.java))
        }

        val nav = findViewById<BottomNavigationView>(R.id.bottomNav)
        nav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> { finish(); true }
                R.id.nav_profile -> { toast("Profile"); true }
                R.id.nav_settings -> { toast("Settings"); true }
                else -> false
            }
        }
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}