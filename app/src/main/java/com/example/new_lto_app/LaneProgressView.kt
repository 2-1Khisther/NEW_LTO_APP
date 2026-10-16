package com.example.new_lto_app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

// Progress bar drawn as a road lane: a track with a dashed centre line,
// filled in olive up to the current position. Used for exam progress.
class LaneProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val density = resources.displayMetrics.density
    private val dashLength = 10 * density
    private val dashGap = 8 * density
    private val dashThickness = 2 * density

    private val trackPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.olive_400)
    }
    // Dashes are light on the open road and navy on the stretch already covered
    private val dashOnTrack = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dashOnFill = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.navy_900)
        alpha = 110
    }
    private val rect = RectF()

    private var current = 0
    private var max = 1

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.LaneProgressView)
        trackPaint.color = a.getColor(
            R.styleable.LaneProgressView_laneTrackColor,
            ContextCompat.getColor(context, R.color.navy_700)
        )
        dashOnTrack.color = a.getColor(
            R.styleable.LaneProgressView_laneDashColor,
            ContextCompat.getColor(context, R.color.cream_50)
        )
        dashOnTrack.alpha = 140
        a.recycle()
        importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
    }

    // label is what TalkBack reads, e.g. "Question" -> "Question 3 of 60"
    fun setProgress(current: Int, max: Int, label: String = "Progress") {
        this.max = max.coerceAtLeast(1)
        this.current = current.coerceIn(0, this.max)
        contentDescription = "$label ${this.current} of ${this.max}"
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val defaultHeight = (12 * density).toInt()
        setMeasuredDimension(
            getDefaultSize(suggestedMinimumWidth, widthMeasureSpec),
            resolveSize(defaultHeight, heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas) {
        val w = width.toFloat()
        val h = height.toFloat()
        val r = h / 2
        val fillEnd = w * current / max

        rect.set(0f, 0f, w, h)
        canvas.drawRoundRect(rect, r, r, trackPaint)

        if (fillEnd > 0f) {
            rect.set(0f, 0f, fillEnd.coerceAtLeast(h), h)
            canvas.drawRoundRect(rect, r, r, fillPaint)
        }

        // Centre line: start after the rounded cap, stop before the far one
        val top = (h - dashThickness) / 2
        var x = r
        while (x < w - r) {
            val end = minOf(x + dashLength, w - r)
            rect.set(x, top, end, top + dashThickness)
            canvas.drawRoundRect(rect, dashThickness, dashThickness,
                if (end <= fillEnd) dashOnFill else dashOnTrack)
            x += dashLength + dashGap
        }
    }
}
