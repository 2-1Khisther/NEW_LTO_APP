package com.example.new_lto_app


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.progressindicator.CircularProgressIndicator

class ExamResultsActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_CATEGORY = "extra_category"
        private const val EXTRA_CORRECT = "extra_correct"
        private const val EXTRA_INCORRECT = "extra_incorrect"
        private const val EXTRA_UNANSWERED = "extra_unanswered"
        private const val EXTRA_TOTAL = "extra_total"

        // The minimum score percentage needed to pass.
        // TODO: adjust if the real LTO passing requirement is different
        // (and consider making this different per category if needed).
        private const val PASSING_PERCENT = 80

        fun newIntent(
            context: Context,
            categoryDisplayName: String,
            correct: Int,
            incorrect: Int,
            unanswered: Int,
            total: Int
        ): Intent {
            return Intent(context, ExamResultsActivity::class.java).apply {
                putExtra(EXTRA_CATEGORY, categoryDisplayName)
                putExtra(EXTRA_CORRECT, correct)
                putExtra(EXTRA_INCORRECT, incorrect)
                putExtra(EXTRA_UNANSWERED, unanswered)
                putExtra(EXTRA_TOTAL, total)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam_results)

        val categoryName = intent.getStringExtra(EXTRA_CATEGORY) ?: ""
        val correct = intent.getIntExtra(EXTRA_CORRECT, 0)
        val incorrect = intent.getIntExtra(EXTRA_INCORRECT, 0)
        val unanswered = intent.getIntExtra(EXTRA_UNANSWERED, 0)
        val total = intent.getIntExtra(EXTRA_TOTAL, 1).coerceAtLeast(1) // avoid divide-by-zero

        val percent = (correct * 100) / total
        val passed = percent >= PASSING_PERCENT

        findViewById<TextView>(R.id.tvCategoryName).text = categoryName
        findViewById<TextView>(R.id.tvScorePercent).text = "$percent%"
        findViewById<CircularProgressIndicator>(R.id.scoreProgress).progress = percent
        findViewById<TextView>(R.id.tvCorrectCount).text = correct.toString()
        findViewById<TextView>(R.id.tvIncorrectCount).text = incorrect.toString()
        findViewById<TextView>(R.id.tvUnansweredCount).text = unanswered.toString()

        val tvPassFail = findViewById<TextView>(R.id.tvPassFail)
        if (passed) {
            tvPassFail.text = "PASSED"
            tvPassFail.setBackgroundResource(R.drawable.bg_pass_badge)
        } else {
            tvPassFail.text = "FAILED"
            tvPassFail.setBackgroundResource(R.drawable.bg_fail_badge)
        }

        // TODO: once Room is wired up, save this result here, e.g.
        // examResultDao.insert(ExamResult(category, correct, incorrect, unanswered, total, timestamp))

        findViewById<TextView>(R.id.btnReviewAnswers).setOnClickListener {
            // TODO: launch ReviewActivity pre-filled with this attempt's actual
            // answers once MockExamActivity passes them along, instead of the
            // generic category review content it shows today.
            finish()
        }

        findViewById<TextView>(R.id.btnBackToHome).setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(homeIntent)
            finish()
        }
    }
}