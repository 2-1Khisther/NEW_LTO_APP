package com.example.new_lto_app

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// One road sign entry: image + name + what it means.
// TODO: replace this hardcoded list with real road signs from your database,
// and swap the drawable res for each sign's actual icon.
data class RoadSign(
    val imageRes: Int,
    val name: String,
    val description: String
)

class RoadSignsActivity : AppCompatActivity() {

    private val signs = listOf(
        RoadSign(
            imageRes = R.drawable.bg_placeholder, // TODO: replace with your "Road Narrows" icon
            name = "Road Narrows",
            description = "Is a warning sign indicating that the road ahead is not as wide as the current road."
        )
        // TODO: add the rest of your road signs here
    )

    private var currentIndex = 0

    private lateinit var tvProgressCount: TextView
    private lateinit var imgRoadSign: ImageView
    private lateinit var tvSignName: TextView
    private lateinit var tvSignDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_road_signs)

        tvProgressCount = findViewById(R.id.tvProgressCount)
        imgRoadSign = findViewById(R.id.imgRoadSign)
        tvSignName = findViewById(R.id.tvSignName)
        tvSignDescription = findViewById(R.id.tvSignDescription)

        showSign(currentIndex)

        findViewById<TextView>(R.id.btnEnd).setOnClickListener {
            finish() // closes this screen and returns to the Home Page
        }

        findViewById<TextView>(R.id.btnNext).setOnClickListener {
            if (currentIndex < signs.size - 1) {
                currentIndex++
                showSign(currentIndex)
            } else {
                toast("That was the last sign")
            }
        }
    }

    private fun showSign(index: Int) {
        val sign = signs[index]
        tvProgressCount.text = "${index + 1} of ${signs.size}"
        imgRoadSign.setImageResource(sign.imageRes)
        tvSignName.text = sign.name
        tvSignDescription.text = sign.description
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}