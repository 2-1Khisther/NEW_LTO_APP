package com.example.new_lto_app

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// Read-only walkthrough of a finished mock exam: shows each question with the
// correct answer highlighted green and the user's wrong pick (if any) in red.
class ExamReviewActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_QUESTIONS = "extra_questions"
        private const val EXTRA_USER_ANSWERS = "extra_user_answers"

        fun newIntent(
            context: Context,
            questions: ArrayList<ExamQuestion>,
            userAnswers: ArrayList<String?>
        ): Intent {
            return Intent(context, ExamReviewActivity::class.java).apply {
                putExtra(EXTRA_QUESTIONS, questions)
                putExtra(EXTRA_USER_ANSWERS, userAnswers)
            }
        }
    }

    private lateinit var questions: List<ExamQuestion>
    private lateinit var userAnswers: List<String?>
    private var currentIndex = 0

    private lateinit var tvQuestionNumber: TextView
    private lateinit var tvProgressCount: TextView
    private lateinit var tvAnswerStatus: TextView
    private lateinit var tvQuestionText: TextView
    private lateinit var optionViews: Map<String, TextView>
    private lateinit var btnNextQuestion: TextView

    @Suppress("DEPRECATION", "UNCHECKED_CAST") // getSerializableExtra(name, Class) needs API 33
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam_review)

        questions = intent.getSerializableExtra(EXTRA_QUESTIONS) as? ArrayList<ExamQuestion> ?: arrayListOf()
        userAnswers = intent.getSerializableExtra(EXTRA_USER_ANSWERS) as? ArrayList<String?> ?: arrayListOf()

        // Guard: nothing to review (or answers don't line up) -> don't crash
        if (questions.isEmpty() || userAnswers.size != questions.size) {
            toast("No exam answers to review")
            finish()
            return
        }

        tvQuestionNumber = findViewById(R.id.tvQuestionNumber)
        tvProgressCount = findViewById(R.id.tvProgressCount)
        tvAnswerStatus = findViewById(R.id.tvAnswerStatus)
        tvQuestionText = findViewById(R.id.tvQuestionText)
        optionViews = mapOf(
            "A" to findViewById(R.id.optionA),
            "B" to findViewById(R.id.optionB),
            "C" to findViewById(R.id.optionC),
            "D" to findViewById(R.id.optionD)
        )
        btnNextQuestion = findViewById(R.id.btnNextQuestion)

        showQuestion(currentIndex)

        findViewById<TextView>(R.id.btnPrevious).setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                showQuestion(currentIndex)
            }
        }

        btnNextQuestion.setOnClickListener {
            if (currentIndex < questions.size - 1) {
                currentIndex++
                showQuestion(currentIndex)
            } else {
                finish() // "Done" on the last question -> back to Exam Results
            }
        }
    }

    private fun showQuestion(index: Int) {
        val q = questions[index]
        val userAnswer = userAnswers[index]

        tvQuestionNumber.text = "QUESTION ${index + 1}"
        tvProgressCount.text = "${index + 1} of ${questions.size}"
        tvQuestionText.text = q.text

        when {
            userAnswer == null -> setStatus("Skipped", "#6B6B6B")
            userAnswer == q.correctAnswer -> setStatus("Correct", "#2E7D32")
            else -> setStatus("Incorrect", "#C0392B")
        }

        val optionTexts = mapOf("A" to q.optionA, "B" to q.optionB, "C" to q.optionC, "D" to q.optionD)
        for ((letter, view) in optionViews) {
            val isCorrect = letter == q.correctAnswer
            val isWrongPick = letter == userAnswer && !isCorrect
            val suffix = when {
                isCorrect && letter == userAnswer -> "   ✓ Your answer"
                isCorrect -> "   ✓ Correct answer"
                isWrongPick -> "   ✗ Your answer"
                else -> ""
            }
            view.text = "$letter.  ${optionTexts[letter]}$suffix"
            when {
                isCorrect -> view.setBackgroundResource(R.drawable.bg_answer_correct)
                isWrongPick -> view.setBackgroundResource(R.drawable.bg_answer_wrong)
                else -> view.background = null
            }
            view.setTypeface(null, if (isCorrect || isWrongPick) Typeface.BOLD else Typeface.NORMAL)
        }

        findViewById<TextView>(R.id.btnPrevious).alpha = if (index == 0) 0.4f else 1f
        btnNextQuestion.text = if (index == questions.size - 1) "Done" else "Next Question"
    }

    private fun setStatus(label: String, color: String) {
        tvAnswerStatus.text = label
        tvAnswerStatus.setTextColor(Color.parseColor(color))
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
