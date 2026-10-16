package com.example.new_lto_app

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.progressindicator.CircularProgressIndicator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // First launch: ask to log in or continue offline
        if (!SessionManager(this).hasChosenMode()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
        setContentView(R.layout.activity_main)
        // Gradient runs under the status bar; the bottom nav pads itself
        InsetsHelper.padForSystemBars(findViewById(R.id.scrollDashboard), top = true)

        // Greet by first name; offline users have no name
        val firstName = SessionManager(this).fullName?.trim()?.substringBefore(' ')
        findViewById<TextView>(R.id.tvWelcome).text =
            if (firstName.isNullOrEmpty()) "Hi there" else "Hi, $firstName"

        // Dynamic values (replace with real data later)
        val score = 83
        val mockCompleted = 70
        findViewById<TextView>(R.id.tvOverallScore).text = "$score%"
        findViewById<CircularProgressIndicator>(R.id.donutScore).progress = score
        findViewById<TextView>(R.id.tvMockCompleted).text = "$mockCompleted%"
        findViewById<LaneProgressView>(R.id.laneMock).apply {
            setProgress(mockCompleted, 100)
            contentDescription = "Mock exams completed: $mockCompleted%"
        }

        // Category cards (hook these up to your next screens later)
        findViewById<MaterialCardView>(R.id.cardNonPro).setOnClickListener {
            startActivity(Intent(this, NonProfessionalActivity::class.java))
        }
        findViewById<MaterialCardView>(R.id.cardPro).setOnClickListener {
            startActivity(Intent(this, ProfessionalActivity::class.java))
        }
        findViewById<MaterialCardView>(R.id.cardRoadSigns).setOnClickListener {
            startActivity(Intent(this, RoadSignsActivity::class.java))
        }
        findViewById<MaterialCardView>(R.id.cardFines).setOnClickListener {
            startActivity(Intent(this, FinesPenaltiesActivity::class.java))
        }

        findViewById<ImageView>(R.id.btnTranslate).setOnClickListener { LanguageHelper.showPicker(this) }

        val nav = findViewById<BottomNavigationView>(R.id.bottomNav)
        BottomNavHelper.setup(this, nav, BottomNavHelper.Screen.HOME)
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
