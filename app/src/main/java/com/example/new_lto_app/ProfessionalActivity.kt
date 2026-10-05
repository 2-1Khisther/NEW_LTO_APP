package com.example.new_lto_app


import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfessionalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professional)

        // Back arrow closes this screen and returns to the Home Page
        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        findViewById<android.widget.Button>(R.id.btnStartMockExam).setOnClickListener {
            startActivity(MockExamActivity.newIntent(this, MockExamActivity.CATEGORY_PROFESSIONAL))
        }

        findViewById<android.widget.Button>(R.id.btnStartReview).setOnClickListener {
            startActivity(ReviewActivity.newIntent(this, ReviewActivity.CATEGORY_PROFESSIONAL))
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