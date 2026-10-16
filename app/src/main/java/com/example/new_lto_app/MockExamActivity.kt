package com.example.new_lto_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

// Serializable so a finished exam can be passed to the Results and Review screens
data class ExamQuestion(
    val text: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: String
) : Serializable

class MockExamActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CATEGORY = "extra_category"
        const val EXTRA_VEHICLE_TYPE = "extra_vehicle_type" // NEW

        const val CATEGORY_NON_PROFESSIONAL = "non_professional"
        const val CATEGORY_PROFESSIONAL = "professional"
        const val CATEGORY_ROAD_SIGNS = "road_signs"
        const val CATEGORY_FINES_PENALTIES = "fines_penalties"

        // LTO manual exam format: 60 items per exam
        const val EXAM_ITEM_COUNT = 60

        // NEW: vehicleType is now required for Non-Professional/Professional.
        // Pass null for Road Signs/Fines, which don't have this distinction.
        fun newIntent(context: Context, category: String, vehicleType: String? = null): Intent {
            return Intent(context, MockExamActivity::class.java).apply {
                putExtra(EXTRA_CATEGORY, category)
                putExtra(EXTRA_VEHICLE_TYPE, vehicleType)
            }
        }

        private fun displayNameFor(category: String, vehicleType: String?): String {
            val categoryName = when (category) {
                CATEGORY_PROFESSIONAL -> "Professional"
                CATEGORY_ROAD_SIGNS -> "Road Signs"
                CATEGORY_FINES_PENALTIES -> "Fines And Penalties"
                else -> "Non-Professional"
            }
            val vehicleLabel = when (vehicleType) {
                VehicleTypeHelper.MOTORCYCLE -> " (Motorcycle)"
                VehicleTypeHelper.LIGHT -> " (Light Vehicle)"
                else -> ""
            }
            return categoryName + vehicleLabel
        }
    }

    // NEW: question lists now split by vehicle type — doubled from 2 lists to 4
    private val nonProfessionalMotorcycleQuestions = listOf<ExamQuestion>()

    private val nonProfessionalLightQuestions = listOf(
        ExamQuestion(
            "The minimum age in the application for Non-Professional Driver's License is:",
            "18 years old", "14 years old", "16 years old", "19 years old",
            correctAnswer = "A"
        )
    )

    private val professionalMotorcycleQuestions = listOf<ExamQuestion>()

    private val professionalLightQuestions = listOf(
        ExamQuestion(
            "Is it legal to have an open exhaust on motorcycle or tricycles?",
            "Yes, in areas requiring water and crossing unit",
            "Yes, if the vehicle is experiencing low power",
            "No, it cannot be legal",
            "Not of the above",
            correctAnswer = "C"
        )
    )

    private val roadSignsQuestions = listOf<ExamQuestion>()
    private val finesPenaltiesQuestions = listOf<ExamQuestion>()

    private lateinit var questions: List<ExamQuestion>
    private lateinit var category: String
    private var vehicleType: String? = null
    private var currentIndex = 0
    private val userAnswers = mutableListOf<String?>()

    private lateinit var tvQuestionNumber: TextView
    private lateinit var tvProgressCount: TextView
    private lateinit var tvQuestionText: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var optionA: RadioButton
    private lateinit var optionB: RadioButton
    private lateinit var optionC: RadioButton
    private lateinit var optionD: RadioButton
    private lateinit var laneExam: LaneProgressView
    private lateinit var btnNextQuestion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mock_exam)
        InsetsHelper.padForSystemBars(findViewById(R.id.topBar), top = true)
        InsetsHelper.padForSystemBars(findViewById(R.id.actionBar), bottom = true)

        category = intent.getStringExtra(EXTRA_CATEGORY) ?: CATEGORY_NON_PROFESSIONAL
        vehicleType = intent.getStringExtra(EXTRA_VEHICLE_TYPE)

        // NEW: now branches on BOTH category AND vehicleType
        val questionPool = when (category) {
            CATEGORY_PROFESSIONAL -> if (vehicleType == VehicleTypeHelper.MOTORCYCLE)
                professionalMotorcycleQuestions else professionalLightQuestions
            CATEGORY_ROAD_SIGNS -> roadSignsQuestions
            CATEGORY_FINES_PENALTIES -> finesPenaltiesQuestions
            else -> if (vehicleType == VehicleTypeHelper.MOTORCYCLE)
                nonProfessionalMotorcycleQuestions else nonProfessionalLightQuestions
        }

        // Draw a random set of up to 60 items from the pool, like the real exam
        questions = questionPool.shuffled().take(EXAM_ITEM_COUNT)

        if (questions.isEmpty()) {
            Toast.makeText(this, "No questions yet for this selection", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        tvQuestionNumber = findViewById(R.id.tvQuestionNumber)
        tvProgressCount = findViewById(R.id.tvProgressCount)
        tvQuestionText = findViewById(R.id.tvQuestionText)
        radioGroup = findViewById(R.id.radioGroupOptions)
        optionA = findViewById(R.id.optionA)
        optionB = findViewById(R.id.optionB)
        optionC = findViewById(R.id.optionC)
        optionD = findViewById(R.id.optionD)
        laneExam = findViewById(R.id.laneExam)
        btnNextQuestion = findViewById(R.id.btnNextQuestion)

        repeat(questions.size) { userAnswers.add(null) }
        showQuestion(currentIndex)

        findViewById<TextView>(R.id.btnSkip).setOnClickListener { goToNextQuestion() }

        findViewById<TextView>(R.id.btnEndExam).setOnClickListener {
            saveCurrentAnswer()
            finishExam()
        }

        btnNextQuestion.setOnClickListener {
            saveCurrentAnswer()
            goToNextQuestion()
        }
    }

    private fun showQuestion(index: Int) {
        val q = questions[index]
        tvQuestionNumber.text = "Question ${index + 1}"
        tvProgressCount.text = "of ${questions.size}"
        laneExam.setProgress(index + 1, questions.size, "Question")
        btnNextQuestion.text = if (index == questions.size - 1) "Finish exam" else "Next question"
        tvQuestionText.text = q.text
        optionA.text = "A.  ${q.optionA}"
        optionB.text = "B.  ${q.optionB}"
        optionC.text = "C.  ${q.optionC}"
        optionD.text = "D.  ${q.optionD}"

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
                categoryDisplayName = displayNameFor(category, vehicleType), // NEW: includes vehicle type
                correct = correct,
                incorrect = incorrect,
                unanswered = unanswered,
                total = questions.size,
                questions = ArrayList(questions),
                userAnswers = ArrayList(userAnswers)
            )
        )
        finish()
    }
}