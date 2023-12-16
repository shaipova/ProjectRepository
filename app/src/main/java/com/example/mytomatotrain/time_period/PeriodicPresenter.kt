package com.example.mytomatotrain.time_period

import android.util.Log
import android.view.View
import com.example.mytomatotrain.Navigator
import com.example.mytomatotrain.TaskPresenter
import com.example.mytomatotrain.db.Repository
import com.example.mytomatotrain.task.Periodic
import com.example.mytomatotrain.task.ScheduleCardInfo
import com.example.mytomatotrain.task.Task
import com.example.mytomatotrain.utils.emptyScheduleCardInfo
import com.example.mytomatotrain.utils.getScheduleCardInfo

interface AdapterCallback {
    fun onTaskClicked(task: Task)
}

class PeriodicPresenter(
    private val repository: Repository
) : TaskPresenter, AdapterCallback {

    private var view: PeriodicView? = null
    private var periodic: Periodic? = null

    override fun attachView(view: View) {
        this.view = view as PeriodicView
    }

    override fun detachView() {
        if (view != null) view = null
    }

    override fun setPeriodic(periodic: Periodic) {
        this.periodic = periodic

    }

    override fun onTaskClicked(task: Task) {
        view?.navigateToTimer(task, )
    }

    override fun setContent() {
        periodic?.let {
            repository.getAllTasksFromPeriod(periodic!!)
                .subscribe( {
                    val cardInfo = getScheduleCardInfo(it)
                    view?.setPeriodicInfo(cardInfo)
                    view?.setTasksList(it, callback = this)
                }, {
                    Log.i("testTag", "error get All Tasks From Periodic: $periodic")
                }
                )
        }

    }

    override fun setListeners() {
        //
    }


}