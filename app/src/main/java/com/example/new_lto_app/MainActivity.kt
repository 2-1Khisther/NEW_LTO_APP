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
import com.google.android.material.progressindicator.LinearProgressIndicator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Dynamic values (replace with real data later)
        val score = 83
        val mockCompleted = 70
        findViewById<TextView>(R.id.tvOverallScore).text = "$score%"
        findViewById<LinearProgressIndicator>(R.id.progressOverall).progress = score
        findViewById<CircularProgressIndicator>(R.id.donutScore).progress = score
        findViewById<LinearProgressIndicator>(R.id.progressMock).progress = mockCompleted

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

        findViewById<ImageView>(R.id.btnTranslate).setOnClickListener { toast("Language picker") }

        val nav = findViewById<BottomNavigationView>(R.id.bottomNav)
        BottomNavHelper.setup(this, nav, BottomNavHelper.Screen.HOME)
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}