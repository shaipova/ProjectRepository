package com.example.mytomatotrain

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class ScheduleCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, // нужно потому что юзаем xml
    defStyleAttr: Int = 0, // нужно потому что будем юзать тему
    defStyleRes: Int = 0 // может быть нужно для использования ресурса стиля
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.schedule_card_constraint, this)
    }

    private val estimatedTimeTV: TextView = findViewById(R.id.estimated_time_text_view)
    private val taskToDoTV: TextView = findViewById(R.id.task_to_do_text_view)
    private val taskDoneTV: TextView = findViewById(R.id.task_done_text_view)

    fun setContent(estimatedTime: String, taskToDo: String, taskDone: String) {
        estimatedTimeTV.text = estimatedTime
        taskToDoTV.text = taskToDo
        taskDoneTV.text = taskDone
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        setMeasuredDimension(
//            MeasureSpec.getSize((widthMeasureSpec)),
//            MeasureSpec.getSize(heightMeasureSpec)
//        )
//    }
}