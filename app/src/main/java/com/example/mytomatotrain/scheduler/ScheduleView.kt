package com.example.mytomatotrain.scheduler

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.mytomatotrain.R
import com.example.mytomatotrain.ScheduleCard
import com.example.mytomatotrain.task.Periodic
import com.example.mytomatotrain.task.ScheduleCardInfo
import com.example.mytomatotrain.utils.Constants.PERIODIC_KEY

interface ScheduleView {
    fun setSchedulePeriodicCard(periodic: Periodic, info: ScheduleCardInfo)
}

class ScheduleViewImpl(val view: View): View(view.context), ScheduleView {


    private val cardContainer = view.findViewById<LinearLayout>(R.id.schedule_periodic_card_container)
    private val context = this.getContext()

    init {
        val addNewTaskButton = view.findViewById<TextView>(R.id.schedule_add_new_task)
        addNewTaskButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_scheduleFragment_to_createTaskFragment)
        }
        val startATaskButton = view.findViewById<Button>(R.id.schedule_start_a_task)
        startATaskButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_scheduleFragment_to_timerFragment)
        }
    }

    override fun setSchedulePeriodicCard(
        periodic: Periodic,
        info: ScheduleCardInfo
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
                val bundle = Bundle()
                bundle.putSerializable(PERIODIC_KEY, periodic)
                view.findNavController().navigate(R.id.action_scheduleFragment_to_periodicFragment, bundle)
            }
            setSchedule(card)
        }

        cardContainer.addView(schedulePeriodicCard)
    }
}