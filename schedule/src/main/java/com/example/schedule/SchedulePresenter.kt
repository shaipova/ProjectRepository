package com.example.schedule

import android.view.View
import kotlinx.coroutines.Job
import com.example.base_util.PresenterScope
import com.example.base_util.getScheduleCardInfoForPeriodic
import com.example.core_api.database.TasksRepository
import com.example.core_api.dto.Periodic
import kotlinx.coroutines.launch
import javax.inject.Inject

class SchedulePresenter @Inject constructor(
    private val repository: TasksRepository
) {
    private var view: ScheduleView? = null
    private var job: Job? = null

    fun attachView(view: View) {
        this.view = view as ScheduleView
    }

    fun detachView() {
        if (view != null) view = null
        job?.cancel()
    }

    fun setContent(listener: (Periodic) -> Unit) {
        job = PresenterScope().launch {
            getCardContent(listener)
        }
    }

    fun setListeners(addNewTaskListener: () -> Unit, startTaskListener: () -> Unit) {
        view?.setClickListener(
            addNewTaskListener = { addNewTaskListener() },
            startTaskListener = { startTaskListener() }
        )
    }

    private suspend fun getCardContent(listener: (Periodic) -> Unit) {
        val listTask = repository.getAllTasks()

        if (listTask.isNullOrEmpty()) {
            view?.setOverlayVisibility(true)
        } else {
            setPeriodicCard(Periodic.EVERYDAY_TASK, listener)
            setPeriodicCard(Periodic.WEEKLY_TASK, listener)
            view?.setOverlayVisibility(false)
        }
    }

    private suspend fun setPeriodicCard(periodic: Periodic, listener: (Periodic) -> Unit) {
        val listPeriodicTask = repository.getAllTasksFromPeriod(periodic)
        val cardInfo = getScheduleCardInfoForPeriodic(listPeriodicTask)
        view?.setSchedulePeriodicCard(periodic, cardInfo) {
            listener.invoke(it)
        }
    }

}