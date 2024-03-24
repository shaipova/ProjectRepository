package com.example.time_period_schedule

import android.view.View
import com.example.base_util.AdapterCallback
import com.example.base_util.PresenterScope
import com.example.base_util.getScheduleCardInfoForPeriodic
import com.example.core_api.database.TasksRepository
import com.example.core_api.dto.Periodic
import com.example.core_api.dto.Task
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


class TimePeriodSchedulePresenter @Inject constructor(
    private val repository: TasksRepository
) : AdapterCallback {

    private var view: TimePeriodScheduleView? = null
    private var periodic: Periodic? = null
    private lateinit var timerStartListener: (Task) -> Unit
    private var job: Job? = null

    fun attachView(view: View) {
        this.view = view as TimePeriodScheduleView
        this.view?.setAdapters(this)
    }

    fun detachView() {
        if (view != null) view = null
        job?.cancel()
    }

    fun setPeriodic(periodic: Periodic?) {
        this.periodic = periodic
    }

    override fun onTaskClicked(task: Task) {
        timerStartListener.invoke(task)
    }

    override fun onDoneTaskClicked(task: Task) {
        job = PresenterScope().launch {
            repository.refreshTask(id = task.id)
            setContent()
        }
    }

    override fun onDeleteClicked(task: Task) {
        job = PresenterScope().launch {
            repository.deleteTask(task)
            setContent()
        }
    }

    override fun onMarkAsDoneClicked(task: Task) {
        job = PresenterScope().launch {
            repository.markTaskDone(task.id)
            setContent()
        }
    }

    fun setContent() {
        job = PresenterScope().launch {
            val periodicTaskList = periodic?.let { repository.getAllTasksFromPeriod(it) }
            val cardInfo = getScheduleCardInfoForPeriodic(periodicTaskList)
            val doneList = periodicTaskList?.filter { it.isTaskComplete }
            val taskInProgress = periodicTaskList?.filterNot { it.isTaskComplete }

            view?.setPeriodicInfo(cardInfo)
            view?.setTasksLists(taskInProgress, doneList)
        }
    }

    fun setListeners(addNewTaskListener: () -> Unit, startTimerListener: (Task) -> Unit) {
        view?.setAddNewTaskListener(addNewTaskListener)
        timerStartListener = startTimerListener
    }


}