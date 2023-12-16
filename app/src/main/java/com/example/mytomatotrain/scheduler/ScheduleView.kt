package com.example.mytomatotrain.scheduler

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginBottom
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


    private val cardContainer = view.findViewById<LinearLayout>(R.id.schedule_card_container)

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
        val periodicTitle = TextView(this.context).apply {
            // size, style
            text = periodic.title
            setPadding(0,12,0,12)
        }

        val card = ScheduleCard(this.context).apply {
            setContent(
                estimatedTime = info.estimatedTime,
                taskToDo = info.taskToDo.toString(),
                taskDone = info.taskDone.toString()
            )
            setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable(PERIODIC_KEY, periodic)
                view.findNavController().navigate(R.id.action_scheduleFragment_to_periodicFragment, bundle)
            }
        }
        cardContainer.addView(periodicTitle)
        cardContainer.addView(card)
    }
}