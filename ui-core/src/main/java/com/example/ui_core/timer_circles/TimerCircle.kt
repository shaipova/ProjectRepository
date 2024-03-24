package com.example.ui_core.timer_circles

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat.getColor
import com.example.ui_core.R

abstract class TimerCircle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0): View(context, attrs, defStyleAttr, defStyleRes) {

    abstract fun setCircleColor(color: Int)
    abstract fun startAnimation()
    abstract fun stopAnimation()
}

class TimerCircleImpl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : TimerCircle(context, attrs, defStyleAttr, defStyleRes) {

    private var rotateAnimator: ObjectAnimator? = null

    override fun setCircleColor(color: Int) {
        val tintColor = getColor(context, color)
        shapesArray.forEach { it?.setTint(tintColor) }
    }

    private val shapeLarge: Drawable? = AppCompatResources.getDrawable(context, R.drawable.shape_for_timer_300)
    private val shapeMedium: Drawable? = AppCompatResources.getDrawable(context, R.drawable.shape_for_timer_262)
    private val shapeSmall: Drawable? = AppCompatResources.getDrawable(context, R.drawable.shape_for_timer_232)
    private val shapesArray = arrayOf(shapeLarge, shapeMedium, shapeSmall)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (i in shapesArray.indices) {
            shapesArray[i].let {
                val intrinsicWidth = it?.intrinsicWidth ?: (width / 2)
                val intrinsicHeight = it?.intrinsicHeight ?: (height / 2)
                it?.setBounds(100+i*40, 100+i*40, intrinsicWidth+20+i*50, intrinsicHeight+20+i*50)
                it?.draw(canvas)
            }
        }
    }

    override fun startAnimation() {
        if (rotateAnimator?.isStarted == true) {
            rotateAnimator?.resume()
        } else {
            rotateAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f).apply {
                duration = 2000
                interpolator = LinearInterpolator()
                repeatCount = ObjectAnimator.INFINITE
                start()
            }
        }
    }

    override fun stopAnimation() {
        rotateAnimator?.pause()
    }
}