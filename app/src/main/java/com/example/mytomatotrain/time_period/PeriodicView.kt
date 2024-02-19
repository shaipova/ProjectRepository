package com.example.mytomatotrain.time_period

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mytomatotrain.R
import com.example.mytomatotrain.ScheduleCard
import com.example.mytomatotrain.adapters.PeriodicTaskListAdapter
import com.example.mytomatotrain.task.ScheduleCardInfo
import com.example.mytomatotrain.task.Task
import com.example.mytomatotrain.utils.Constants.TASK_KEY

interface PeriodicView {
    fun setPeriodicInfo(info: ScheduleCardInfo)
    fun setTasksList(list: List<Task>, callback: AdapterCallback)
    fun navigateToTimer(task: Task)

}

class PeriodicViewImpl(val view: View): View(view.context), PeriodicView {

    private val periodicInfoCard: ScheduleCard = view.findViewById(R.id.periodic_schedule_card)
    private val recyclerView: RecyclerView = view.findViewById(R.id.periodic_recycler_view)
    private val recyclerViewDoneTasks: RecyclerView = view.findViewById(R.id.periodic_recycler_view_done_tasks)
    private val doneTitle: TextView = view.findViewById(R.id.periodic_done_title)

    override fun setPeriodicInfo(info: ScheduleCardInfo) {
        periodicInfoCard.setContent(
            estimatedTime = info.estimatedTime,
            taskToDo = info.taskToDo.toString(),
            taskDone = info.taskDone.toString()
        )
    }

    override fun setTasksList(list: List<Task>, callback: AdapterCallback) {
        // добавить ещё один список для завершенных задач

        val listAdapter = PeriodicTaskListAdapter(callback)
        listAdapter.submitList(list)
        recyclerView.adapter = listAdapter
    }

    override fun navigateToTimer(task: Task) {
        val bundle = Bundle()
        bundle.putSerializable(TASK_KEY, task)
        view.findNavController().navigate(R.id.action_periodicFragment_to_timerFragment, bundle)
    }

}