package com.example.schedule

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.base_util.changeVisibility
import com.example.core_api.dto.Periodic
import com.example.core_api.dto.ScheduleCardInfo
import com.example.ui_core.ScheduleCard

interface ScheduleView {
    fun setSchedulePeriodicCard(periodic: Periodic, info: ScheduleCardInfo, listener: (Periodic) -> Unit
    )
    fun setClickListener(addNewTaskListener: () -> Unit, startTaskListener: () -> Unit)

    fun setOverlayVisibility(isVisible: Boolean)
}

class ScheduleViewImpl(view: View): View(view.context), ScheduleView {


    private val cardContainer = view.findViewById<LinearLayout>(R.id.schedule_periodic_card_container)
    private val context = this.getContext()
    private val addNewTaskButton = view.findViewById<TextView>(R.id.schedule_add_new_task)
    private val startTaskButton = view.findViewById<Button>(R.id.schedule_start_a_task)
    private val withoutTasksOverlay = view.findViewById<LinearLayout>(R.id.schedule_without_tasks_overlay)
    private val withoutTasksOverlayAddTaskButton = withoutTasksOverlay.findViewById<Button>(R.id.schedule_without_tasks_overlay_add_task)

    override fun setOverlayVisibility(isVisible: Boolean) {
        withoutTasksOverlay.changeVisibility(isVisible)
    }

    override fun setClickListener(addNewTaskListener: () -> Unit, startTaskListener: () -> Unit) {
        addNewTaskButton.setOnClickListener {
            addNewTaskListener()
        }
        withoutTasksOverlayAddTaskButton.setOnClickListener {
            addNewTaskListener()
        }
        startTaskButton.setOnClickListener {
            startTaskListener()
        }
    }

    override fun setSchedulePeriodicCard(
        periodic: Periodic,
        info: ScheduleCardInfo,
        listener: (Periodic) -> Unit
    ) {

        val card = ScheduleCard(context).apply {
            setContent(
                estimatedTime = info.estimatedTime,
                taskToDo = info.taskToDo.toString(),
                taskDone = info.taskDone.toString()
            )
        }
        val schedulePeriodicCard = SchedulePeriodicCard(context).apply {
            setPeriodicCardTitle(periodic.title)
            setOnClickListener {
                listener.invoke(periodic)
            }
            setSchedule(card)
        }

        cardContainer.addView(schedulePeriodicCard)
    }
}