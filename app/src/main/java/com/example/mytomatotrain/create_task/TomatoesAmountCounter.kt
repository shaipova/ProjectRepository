package com.example.mytomatotrain.create_task

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.provider.Settings.Global.getString
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mytomatotrain.R
import com.example.mytomatotrain.utils.dp
import io.reactivex.rxjava3.core.Completable

class TomatoesAmountCounter @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        View.inflate(context, R.layout.tomatoes_amout_counter, this)
    }
    private val addTomatoButton: ImageView = findViewById(R.id.add_tomato_button)
    private val tomatoesContainer: LinearLayout = findViewById(R.id.tomatoes_container)
    private val sumTomatoTaskTime: TextView = findViewById(R.id.estimated_task_time_text)

    fun changeSumTomatoTaskTime(estimatedTime: String) {
        sumTomatoTaskTime.text = context.getString(R.string.time_for_this_task, estimatedTime)
    }

    fun setOnAddTomatoClickListener(
        addTomatoListener: (Completable) -> Unit,
        removeTomatoListener: (Completable) -> Unit
    ) {
        addTomatoButton.setOnClickListener {
            tomatoesContainer.addView(
                ImageView(context).apply {
                    setImageResource(R.drawable.icon_tomato_24)
                    setPadding(12.dp, 0, 0,0)
                    setOnLongClickListener {
                        removeTomatoListener(Completable.complete())
                        removeTomato(this)
                        true
                    }
                }.also {
                    ObjectAnimator.ofPropertyValuesHolder(
                        it,
                        PropertyValuesHolder.ofFloat("scaleX", 0.8f, 1f),
                        PropertyValuesHolder.ofFloat("scaleY", 0.8f, 1f)
                    ).apply {
                        duration = 100
                        start()
                    }
                }
            )
            addTomatoListener(Completable.complete())
        }
    }

    private fun removeTomato(view: View) {
        view.animate()
            .scaleX(0f)
            .scaleY(0f)
            .alpha(0.5f)
            .setDuration(300)
            .setInterpolator(AccelerateInterpolator())
            .withEndAction {
                tomatoesContainer.removeView(view)
            }
            .start()
    }

}