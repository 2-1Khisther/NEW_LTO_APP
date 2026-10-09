package com.example.new_lto_app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // TODO: once login + database are built, replace these hardcoded values
        // with the real logged-in user's name and stats pulled from exam_results.
        val userName = "Khristher"
        val questionsAnswered = 68
        val masterAnswer = 68
        val correctAnswered = 68
        val incorrectAnswered = 68

        findViewById<TextView>(R.id.tvUserName).text = userName
        findViewById<TextView>(R.id.tvQuestionAnswered).text = questionsAnswered.toString()
        findViewById<TextView>(R.id.tvMasterAnswer).text = masterAnswer.toString()
        findViewById<TextView>(R.id.tvCorrectAnswered).text = correctAnswered.toString()
        findViewById<TextView>(R.id.tvIncorrectAnswered).text = incorrectAnswered.toString()

        val nav = findViewById<BottomNavigationView>(R.id.bottomNav)
        BottomNavHelper.setup(this, nav, BottomNavHelper.Screen.PROFILE)
    }
}