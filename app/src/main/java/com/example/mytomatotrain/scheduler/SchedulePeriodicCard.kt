package com.example.mytomatotrain.scheduler

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mytomatotrain.R

class SchedulePeriodicCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.schedule_periodic_card, this)
    }

    private val title: TextView = findViewById(R.id.schedule_periodic_title)
    private val cardContainer: FrameLayout = findViewById(R.id.schedule_card_container)

    fun setPeriodicCardTitle(periodic: String) {
        title.text = periodic
    }

    fun setSchedule(card: View) {
        cardContainer.addView(card)
    }
}