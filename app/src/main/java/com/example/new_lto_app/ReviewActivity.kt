package com.example.new_lto_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

data class ReviewItem(
    val questionText: String,
    val answerText: String
)

class ReviewActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CATEGORY = "extra_category"

        const val CATEGORY_NON_PROFESSIONAL = "non_professional"
        const val CATEGORY_PROFESSIONAL = "professional"
        const val CATEGORY_ROAD_SIGNS = "road_signs"
        const val CATEGORY_FINES_PENALTIES = "fines_penalties"

        fun newIntent(context: Context, category: String): Intent {
            return Intent(context, ReviewActivity::class.java).apply {
                putExtra(EXTRA_CATEGORY, category)
            }
        }
    }

    private val nonProfessionalItems = listOf(
        ReviewItem(
            "The minimum age in the application for Non-Professional Driver's License is:",
            "18 years old"
        ),
        ReviewItem(
            "What does a red traffic light mean?",
            "Stop"
        )
    )

    private val professionalItems = listOf(
        ReviewItem(
            "Is it legal to have an open exhaust on motorcycle or tricycles?",
            "No, it cannot be legal"
        )
    )

    private val roadSignsItems = listOf<ReviewItem>()
    private val finesPenaltiesItems = listOf<ReviewItem>()

    private lateinit var items: List<ReviewItem>
    private var currentIndex = 0

    private lateinit var tvQuestionNumber: TextView
    private lateinit var tvProgressCount: TextView
    private lateinit var tvQuestionText: TextView
    private lateinit var tvSelectedAnswer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        val category = intent.getStringExtra(EXTRA_CATEGORY) ?: CATEGORY_NON_PROFESSIONAL
        items = when (category) {
            CATEGORY_PROFESSIONAL -> professionalItems
            CATEGORY_ROAD_SIGNS -> roadSignsItems
            CATEGORY_FINES_PENALTIES -> finesPenaltiesItems
            else -> nonProfessionalItems
        }

        tvQuestionNumber = findViewById(R.id.tvQuestionNumber)
        tvProgressCount = findViewById(R.id.tvProgressCount)
        tvQuestionText = findViewById(R.id.tvQuestionText)
        tvSelectedAnswer = findViewById(R.id.tvSelectedAnswer)

        showItem(currentIndex)

        findViewById<TextView>(R.id.btnEnd).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.btnNextQuestion).setOnClickListener {
            if (currentIndex < items.size - 1) {
                currentIndex++
                showItem(currentIndex)
            } else {
                toast("That was the last item")
            }
        }
    }

    private fun showItem(index: Int) {
        val item = items[index]
        tvQuestionNumber.text = "QUESTION ${index + 1}"
        tvProgressCount.text = "${index + 1} of ${items.size}"
        tvQuestionText.text = item.questionText
        tvSelectedAnswer.text = item.answerText
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}