package app.olauncher.ui.vault

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.hypot
import kotlin.math.min

class PatternLockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    interface OnPatternListener {
        fun onPatternStarted() {}
        fun onPatternEntered(pattern: String)
        fun onPatternCleared() {}
    }

    private val dotRadius: Float = 14f
    private val dotSelectedRadius: Float = 22f
    private val hitRadius: Float = 55f
    private val strokeWidth: Float = 8f

    private var dotColor: Int = Color.parseColor("#888888")
    private var selectedColor: Int = Color.parseColor("#FFFFFF")
    private var errorColor: Int = Color.parseColor("#FF5252")
    private var isError: Boolean = false

    private val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    private data class Dot(val row: Int, val col: Int, val index: Int, var x: Float = 0f, var y: Float = 0f)

    private val dots = Array(3) { row ->
        Array(3) { col ->
            Dot(row, col, row * 3 + col)
        }
    }

    private val selectedDots = mutableListOf<Dot>()
    private var currentX: Float = 0f
    private var currentY: Float = 0f
    private var isTouching: Boolean = false

    private var patternListener: OnPatternListener? = null
    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator

    fun setOnPatternListener(listener: OnPatternListener) {
        this.patternListener = listener
    }

    fun setPatternColors(normalColor: Int, activeColor: Int, errColor: Int) {
        dotColor = normalColor
        selectedColor = activeColor
        errorColor = errColor
        invalidate()
    }

    fun setError(error: Boolean) {
        isError = error
        invalidate()
    }

    fun clearPattern() {
        selectedDots.clear()
        isError = false
        isTouching = false
        invalidate()
        patternListener?.onPatternCleared()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val size = min(w, h)
        val padding = size * 0.15f
        val step = (size - 2 * padding) / 2f
        val offsetX = (w - size) / 2f + padding
        val offsetY = (h - size) / 2f + padding

        for (r in 0 until 3) {
            for (c in 0 until 3) {
                dots[r][c].x = offsetX + c * step
                dots[r][c].y = offsetY + r * step
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val activePaintColor = if (isError) errorColor else selectedColor
        linePaint.color = activePaintColor
        linePaint.strokeWidth = strokeWidth

        // Draw connecting lines between selected dots
        if (selectedDots.isNotEmpty()) {
            val path = Path()
            path.moveTo(selectedDots[0].x, selectedDots[0].y)
            for (i in 1 until selectedDots.size) {
                path.lineTo(selectedDots[i].x, selectedDots[i].y)
            }
            if (isTouching && !isError) {
                path.lineTo(currentX, currentY)
            }
            canvas.drawPath(path, linePaint)
        }

        // Draw dots
        for (r in 0 until 3) {
            for (c in 0 until 3) {
                val dot = dots[r][c]
                val isSelected = selectedDots.contains(dot)
                if (isSelected) {
                    dotPaint.color = activePaintColor
                    canvas.drawCircle(dot.x, dot.y, dotSelectedRadius, dotPaint)
                } else {
                    dotPaint.color = dotColor
                    canvas.drawCircle(dot.x, dot.y, dotRadius, dotPaint)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled) return false

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                clearPattern()
                patternListener?.onPatternStarted()
                isTouching = true
                currentX = event.x
                currentY = event.y
                checkDotHit(event.x, event.y)
                invalidate()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (isTouching) {
                    currentX = event.x
                    currentY = event.y
                    checkDotHit(event.x, event.y)
                    invalidate()
                }
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isTouching = false
                invalidate()
                if (selectedDots.isNotEmpty()) {
                    val patternString = selectedDots.joinToString("") { it.index.toString() }
                    patternListener?.onPatternEntered(patternString)
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun checkDotHit(x: Float, y: Float) {
        for (r in 0 until 3) {
            for (c in 0 until 3) {
                val dot = dots[r][c]
                val dist = hypot((x - dot.x).toDouble(), (y - dot.y).toDouble()).toFloat()
                if (dist <= hitRadius && !selectedDots.contains(dot)) {
                    selectedDots.add(dot)
                    vibrate()
                    return
                }
            }
        }
    }

    private fun vibrate() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(25)
            }
        } catch (_: Exception) {}
    }
}
