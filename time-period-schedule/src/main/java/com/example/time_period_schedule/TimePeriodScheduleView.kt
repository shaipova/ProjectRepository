package com.example.time_period_schedule

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.base_util.AdapterCallback
import com.example.core_api.dto.ScheduleCardInfo
import com.example.core_api.dto.Task
import com.example.ui_core.ScheduleCard
import com.example.ui_core.adapters.DividerItemDecorator
import com.example.ui_core.adapters.PeriodicDoneTaskListAdapter
import com.example.ui_core.adapters.PeriodicTaskListAdapter

interface TimePeriodScheduleView {
    fun setPeriodicInfo(info: ScheduleCardInfo)
    fun setTasksLists(listTaskInProgress: List<Task>?, listTaskDone: List<Task>?)
    fun setAddNewTaskListener(listener: () -> Unit)

    fun setAdapters(callback: AdapterCallback)
}

class TimePeriodScheduleViewImpl(view: View): View(view.context), TimePeriodScheduleView {

    private val periodicInfoCard: ScheduleCard = view.findViewById(R.id.periodic_schedule_card)
    private val recyclerView: RecyclerView = view.findViewById(R.id.periodic_recycler_view)
    private val addNewTaskButton = view.findViewById<TextView>(R.id.time_period_add_new_task)
    private lateinit var listAdapter: PeriodicTaskListAdapter
    private lateinit var doneListAdapter: PeriodicDoneTaskListAdapter


    override fun setPeriodicInfo(info: ScheduleCardInfo) {
        periodicInfoCard.setContent(
            estimatedTime = info.estimatedTime,
            taskToDo = info.taskToDo.toString(),
            taskDone = info.taskDone.toString()
        )
    }

    override fun setTasksLists(listTaskInProgress: List<Task>?, listTaskDone: List<Task>?) {
        listAdapter.submitList(listTaskInProgress)
        doneListAdapter.submitList(listTaskDone)
    }

    override fun setAddNewTaskListener(listener: () -> Unit) {
        addNewTaskButton.setOnClickListener { listener() }
    }

    override fun setAdapters(callback: AdapterCallback) {
        listAdapter = PeriodicTaskListAdapter(callback)
        doneListAdapter = PeriodicDoneTaskListAdapter(callback)

        val concatAdapter = ConcatAdapter(listAdapter, doneListAdapter)

        recyclerView.adapter = concatAdapter
        recyclerView.addItemDecoration(DividerItemDecorator(50))
    }
}