package com.example.mytomatotrain.timer_circles

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.mytomatotrain.R

class FirstVersionTimerCircle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private var circleColor: Int = context.getColor(R.color.violet_basic)
    private val circleRadiusArray = floatArrayOf(300f, 270f, 232f)
    private val circleAlphaArray = intArrayOf(255, 125, 65)
    private var animator: ValueAnimator? = null

    fun setCircleColor(color: Int) {
        circleColor = color
    }

    private val paint = Paint().apply {
        color = circleColor
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        for (i in 0..2) {
            paint.alpha = circleAlphaArray[i]
            val radius = circleRadiusArray[i]
            canvas.drawCircle(
                centerX, centerY, radius, paint
            )
        }
    }

    fun startAnimation() {
        animator = ValueAnimator.ofInt(125, 65)
        animator?.duration = 2000
        animator?.repeatCount = ValueAnimator.INFINITE
        animator?.repeatMode = ValueAnimator.REVERSE
        animator?.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            circleAlphaArray[0] = value
            circleAlphaArray[1] = (value * 0.8).toInt()
            circleAlphaArray[2] = (value * 0.6).toInt()
            invalidate()
        }
    }

    fun stopAnimation() {
        animator?.cancel()
    }

}