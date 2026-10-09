package com.example.new_lto_app

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// One violation entry: name + fine amount + what it means.
// TODO: replace this hardcoded list with real fines/penalties from your database,
// and swap the drawable res for each item's actual icon (e.g. a ticket icon).
data class Violation(
    val imageRes: Int,
    val name: String,
    val fineAmount: String,
    val description: String
)

class FinesPenaltiesActivity : AppCompatActivity() {

    // TODO: add the rest of the LTO's official fines/penalties list here.
    // Double-check exact amounts against the current LTO fine schedule before
    // publishing, since these change over time.
    private val violations = listOf(
        Violation(
            imageRes = R.drawable.bg_placeholder, // TODO: replace with a ticket/violation icon
            name = "Driving Without a License",
            fineAmount = "₱3,000 (1st offense)",
            description = "Operating a motor vehicle on a public road without a valid driver's license issued by the LTO."
        ),
        Violation(
            imageRes = R.drawable.bg_placeholder,
            name = "No Seatbelt",
            fineAmount = "₱1,000 (1st offense)",
            description = "Failure of the driver or front-seat passenger to wear a seatbelt while the vehicle is in motion."
        ),
        Violation(
            imageRes = R.drawable.bg_placeholder,
            name = "Reckless Driving",
            fineAmount = "₱2,000 (1st offense)",
            description = "Driving in a manner that endangers the safety of other drivers, pedestrians, or property."
        )
    )

    private var currentIndex = 0

    private lateinit var tvProgressCount: TextView
    private lateinit var imgViolationIcon: ImageView
    private lateinit var tvViolationName: TextView
    private lateinit var tvFineAmount: TextView
    private lateinit var tvViolationDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fines_penalties)

        tvProgressCount = findViewById(R.id.tvProgressCount)
        imgViolationIcon = findViewById(R.id.imgViolationIcon)
        tvViolationName = findViewById(R.id.tvViolationName)
        tvFineAmount = findViewById(R.id.tvFineAmount)
        tvViolationDescription = findViewById(R.id.tvViolationDescription)

        showViolation(currentIndex)

        findViewById<TextView>(R.id.btnEnd).setOnClickListener {
            finish() // closes this screen and returns to the Home Page
        }

        findViewById<TextView>(R.id.btnNext).setOnClickListener {
            if (currentIndex < violations.size - 1) {
                currentIndex++
                showViolation(currentIndex)
            } else {
                toast("That was the last one")
            }
        }
    }

    private fun showViolation(index: Int) {
        val v = violations[index]
        tvProgressCount.text = "${index + 1} of ${violations.size}"
        imgViolationIcon.setImageResource(v.imageRes)
        tvViolationName.text = v.name
        tvFineAmount.text = v.fineAmount
        tvViolationDescription.text = v.description
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}