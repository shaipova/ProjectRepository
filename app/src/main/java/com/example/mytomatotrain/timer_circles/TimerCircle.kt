package com.example.mytomatotrain.timer_circles

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator

class TimerCircle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, // нужно потому что юзаем xml
    defStyleAttr: Int = 0, // нужно потому что будем юзать тему
    defStyleRes: Int = 0 // может быть нужно для использования ресурса стиля
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private var circleColor: Int = Color.BLUE // some default color
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
        animator = ValueAnimator.ofInt(125, 65) // 125, 65, 125 - ok
        animator?.duration = 2000
        animator?.repeatCount = ValueAnimator.INFINITE
        animator?.repeatMode = ValueAnimator.REVERSE
        animator?.interpolator = AccelerateDecelerateInterpolator()
        animator?.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            circleAlphaArray[0] = value
            circleAlphaArray[1] = (value * 0.8).toInt()
            circleAlphaArray[2] = (value * 0.6).toInt()
            invalidate()
        }
        animator?.start()
    }

    fun stopAnimation() {
        animator?.cancel()
    }

}