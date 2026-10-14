package com.example.new_lto_app

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class NonProfessionalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_non_professional)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        findViewById<Button>(R.id.btnStartMockExam).setOnClickListener {
            VehicleTypeHelper.showPicker(this) { vehicleType ->
                startActivity(
                    MockExamActivity.newIntent(
                        this,
                        MockExamActivity.CATEGORY_NON_PROFESSIONAL,
                        vehicleType
                    )
                )
            }
        }

        findViewById<Button>(R.id.btnStartReview).setOnClickListener {
            VehicleTypeHelper.showPicker(this) { vehicleType ->
                startActivity(
                    ReviewActivity.newIntent(
                        this,
                        ReviewActivity.CATEGORY_NON_PROFESSIONAL,
                        vehicleType
                    )
                )
            }
        }

        val nav = findViewById<BottomNavigationView>(R.id.bottomNav)
        BottomNavHelper.setup(this, nav, BottomNavHelper.Screen.NONE)
    }
}