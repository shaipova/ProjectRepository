package com.example.ui_core

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class ScheduleCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.schedule_card_view, this)
    }

    private val estimatedTimeTV: TextView = findViewById(R.id.estimated_time_text_view)
    private val taskToDoTV: TextView = findViewById(R.id.task_to_do_text_view)
    private val taskDoneTV: TextView = findViewById(R.id.task_done_text_view)

    fun setContent(estimatedTime: String, taskToDo: String, taskDone: String) {
        estimatedTimeTV.text = estimatedTime
        taskToDoTV.text = taskToDo
        taskDoneTV.text = taskDone
    }
}