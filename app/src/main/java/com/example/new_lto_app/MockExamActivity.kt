package com.example.new_lto_app


import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

// Simple local model for one question. Later, replace SAMPLE_QUESTIONS
// with questions pulled from Room (dao.getRandomQuestions(category, 45)).
data class ExamQuestion(
    val text: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: String // "A", "B", "C", or "D"
)

class MockExamActivity : AppCompatActivity() {

    // TODO: replace this hardcoded list with real questions from your database
    private val questions = listOf(
        ExamQuestion(
            "The minimum age in the application for Non-Professional Driver's License is:",
            "18 years old", "14 years old", "16 years old", "19 years old",
            correctAnswer = "A"
        ),
        ExamQuestion(
            "What does a red traffic light mean?",
            "Slow down", "Stop", "Proceed with caution", "Speed up",
            correctAnswer = "B"
        )
        // TODO: add the rest of your 45 questions, or load from the database
    )

    private var currentIndex = 0
    private val userAnswers = mutableListOf<String?>() // stores "A"/"B"/"C"/"D"/null per question

    private lateinit var tvQuestionNumber: TextView
    private lateinit var tvProgressCount: TextView
    private lateinit var tvQuestionText: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var optionA: RadioButton
    private lateinit var optionB: RadioButton
    private lateinit var optionC: RadioButton
    private lateinit var optionD: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mock_exam)

        tvQuestionNumber = findViewById(R.id.tvQuestionNumber)
        tvProgressCount = findViewById(R.id.tvProgressCount)
        tvQuestionText = findViewById(R.id.tvQuestionText)
        radioGroup = findViewById(R.id.radioGroupOptions)
        optionA = findViewById(R.id.optionA)
        optionB = findViewById(R.id.optionB)
        optionC = findViewById(R.id.optionC)
        optionD = findViewById(R.id.optionD)

        // Pre-fill the answers list with "not answered yet"
        repeat(questions.size) { userAnswers.add(null) }

        showQuestion(currentIndex)

        findViewById<TextView>(R.id.btnSkip).setOnClickListener {
            goToNextQuestion() // skip = move on without requiring an answer
        }

        findViewById<TextView>(R.id.btnEndExam).setOnClickListener {
            // TODO: navigate to your Exam Results screen instead of just a toast
            toast("Exam ended")
            finish()
        }

        findViewById<TextView>(R.id.btnNextQuestion).setOnClickListener {
            saveCurrentAnswer()
            goToNextQuestion()
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

    private fun showQuestion(index: Int) {
        val q = questions[index]
        tvQuestionNumber.text = "QUESTION ${index + 1}"
        tvProgressCount.text = "${index + 1} of ${questions.size}"
        tvQuestionText.text = q.text
        optionA.text = "A.  ${q.optionA}"
        optionB.text = "B.  ${q.optionB}"
        optionC.text = "C.  ${q.optionC}"
        optionD.text = "D.  ${q.optionD}"

        // Restore a previously picked answer if the user goes back/forward later
        radioGroup.clearCheck()
        when (userAnswers[index]) {
            "A" -> optionA.isChecked = true
            "B" -> optionB.isChecked = true
            "C" -> optionC.isChecked = true
            "D" -> optionD.isChecked = true
        }
    }

    private fun saveCurrentAnswer() {
        userAnswers[currentIndex] = when (radioGroup.checkedRadioButtonId) {
            R.id.optionA -> "A"
            R.id.optionB -> "B"
            R.id.optionC -> "C"
            R.id.optionD -> "D"
            else -> null
        }
    }

    private fun goToNextQuestion() {
        if (currentIndex < questions.size - 1) {
            currentIndex++
            showQuestion(currentIndex)
        } else {
            // TODO: navigate to your Exam Results screen and pass userAnswers for grading
            toast("That was the last question")
        }
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}