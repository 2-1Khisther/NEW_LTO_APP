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
        const val EXTRA_VEHICLE_TYPE = "extra_vehicle_type"

        const val CATEGORY_NON_PROFESSIONAL = "non_professional"
        const val CATEGORY_PROFESSIONAL = "professional"
        const val CATEGORY_ROAD_SIGNS = "road_signs"
        const val CATEGORY_FINES_PENALTIES = "fines_penalties"

        fun newIntent(context: Context, category: String, vehicleType: String? = null): Intent {
            return Intent(context, ReviewActivity::class.java).apply {
                putExtra(EXTRA_CATEGORY, category)
                putExtra(EXTRA_VEHICLE_TYPE, vehicleType)
            }
        }
    }

    // TODO: add real Motorcycle content (Nonpro manual pp.162-167, Pro pp.173-177)
    private val nonProfessionalMotorcycleItems = listOf<ReviewItem>()

    // TODO: add the rest of the real Non-Professional items from the manual
    private val nonProfessionalLightItems = listOf(
        ReviewItem(
            "If you are parking uphill on a road without a curb, turn the wheels toward the:",
            "Edge of the road"
        ),
        ReviewItem(
            "What does a flashing yellow light mean?",
            "You can proceed through the intersection with caution"
        )
    )

    private val professionalMotorcycleItems = listOf<ReviewItem>()

    // TODO: add the rest of the real Professional items from the manual
    private val professionalLightItems = listOf(
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
        val vehicleType = intent.getStringExtra(EXTRA_VEHICLE_TYPE)

        items = when (category) {
            CATEGORY_PROFESSIONAL -> if (vehicleType == VehicleTypeHelper.MOTORCYCLE)
                professionalMotorcycleItems else professionalLightItems
            CATEGORY_ROAD_SIGNS -> roadSignsItems
            CATEGORY_FINES_PENALTIES -> finesPenaltiesItems
            else -> if (vehicleType == VehicleTypeHelper.MOTORCYCLE)
                nonProfessionalMotorcycleItems else nonProfessionalLightItems
        }

        // Guard: no content yet for this selection -> don't crash
        if (items.isEmpty()) {
            toast("No review material yet for this selection")
            finish()
            return
        }

        tvQuestionNumber = findViewById(R.id.tvQuestionNumber)
        tvProgressCount = findViewById(R.id.tvProgressCount)
        tvQuestionText = findViewById(R.id.tvQuestionText)
        tvSelectedAnswer = findViewById(R.id.tvSelectedAnswer)

        showItem(currentIndex)

        findViewById<TextView>(R.id.btnEnd).setOnClickListener { finish() }

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