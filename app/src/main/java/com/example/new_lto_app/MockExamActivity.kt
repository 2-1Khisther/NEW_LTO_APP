package com.example.new_lto_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Simple local model for one question.
// Later, replace the sample lists below with questions pulled from Room
// (dao.getRandomQuestions(category, 45)).
data class ExamQuestion(
    val text: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: String // "A", "B", "C", or "D"
)

class MockExamActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CATEGORY = "extra_category"

        // Category keys — use these exact strings everywhere (Home Page cards,
        // the database "category" column, and anywhere else you filter by category).
        const val CATEGORY_NON_PROFESSIONAL = "non_professional"
        const val CATEGORY_PROFESSIONAL = "professional"
        const val CATEGORY_ROAD_SIGNS = "road_signs"
        const val CATEGORY_FINES_PENALTIES = "fines_penalties"

        // Helper so other Activities can launch this screen with one line:
        // startActivity(MockExamActivity.newIntent(this, MockExamActivity.CATEGORY_PROFESSIONAL))
        fun newIntent(context: Context, category: String): Intent {
            return Intent(context, MockExamActivity::class.java).apply {
                putExtra(EXTRA_CATEGORY, category)
            }
        }

        // Human-readable label shown on the Exam Results screen
        private fun displayNameFor(category: String): String = when (category) {
            CATEGORY_PROFESSIONAL -> "Professional"
            CATEGORY_ROAD_SIGNS -> "Road Signs"
            CATEGORY_FINES_PENALTIES -> "Fines And Penalties"
            else -> "Non-Professional"
        }
    }

    // TODO: replace these hardcoded lists with real questions from your database,
    // filtered by category, e.g. dao.getRandomQuestions(category, 45)
    private val nonProfessionalQuestions = listOf(
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
    )

    private val professionalQuestions = listOf(
        ExamQuestion(
            "Is it legal to have an open exhaust on motorcycle or tricycles?",
            "Yes, in areas requiring water and crossing unit",
            "Yes, if the vehicle is experiencing low power",
            "No, it cannot be legal",
            "Not of the above",
            correctAnswer = "C"
        )
        // TODO: add the rest of your Professional category questions
    )

    private val roadSignsQuestions = listOf<ExamQuestion>(
        // TODO: add Road Signs questions
    )

    private val finesPenaltiesQuestions = listOf<ExamQuestion>(
        // TODO: add Fines And Penalties questions
    )

    private lateinit var questions: List<ExamQuestion>
    private lateinit var category: String
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

        // Figure out which category we're reviewing, default to Non-Professional
        // if nothing was passed in (so the screen still works if you open it directly).
        category = intent.getStringExtra(EXTRA_CATEGORY) ?: CATEGORY_NON_PROFESSIONAL
        questions = when (category) {
            CATEGORY_PROFESSIONAL -> professionalQuestions
            CATEGORY_ROAD_SIGNS -> roadSignsQuestions
            CATEGORY_FINES_PENALTIES -> finesPenaltiesQuestions
            else -> nonProfessionalQuestions
        }

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
            saveCurrentAnswer() // don't lose whatever's selected on the current question
            finishExam()
        }

        findViewById<TextView>(R.id.btnNextQuestion).setOnClickListener {
            saveCurrentAnswer()
            goToNextQuestion()
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
            finishExam()
        }
    }

    // Grades whatever was answered so far and opens the Exam Results screen.
    // Works the same whether the user finished every question or tapped End
    // Exam partway through — anything still unanswered just counts as skipped.
    private fun finishExam() {
        var correct = 0
        var incorrect = 0
        var unanswered = 0

        for (i in questions.indices) {
            val userAnswer = userAnswers[i]
            when {
                userAnswer == null -> unanswered++
                userAnswer == questions[i].correctAnswer -> correct++
                else -> incorrect++
            }
        }

        startActivity(
            ExamResultsActivity.newIntent(
                context = this,
                categoryDisplayName = displayNameFor(category),
                correct = correct,
                incorrect = incorrect,
                unanswered = unanswered,
                total = questions.size
            )
        )
        finish()
    }
}