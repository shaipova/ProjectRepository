package com.example.mytomatotrain.scheduler

import android.util.Log
import android.view.View
import com.example.mytomatotrain.R
import com.example.mytomatotrain.TaskPresenter
import com.example.mytomatotrain.db.Repository
import com.example.mytomatotrain.task.Periodic
import com.example.mytomatotrain.task.ScheduleCardInfo
import com.example.mytomatotrain.utils.emptyScheduleCardInfo
import com.example.mytomatotrain.utils.getScheduleCardInfo

class SchedulePresenter(
    private val repository: Repository
) : TaskPresenter {

    private var view: ScheduleView? = null
    //private var navigator: Navigator? = null

    override fun attachView(view: View) {
        this.view = view as ScheduleView
    }

//    override fun attachNavigator(navigator: Navigator) {
//        this.navigator = navigator
//    }

    override fun detachView() {
        if (view != null) view = null
    }

    override fun setContent() {
        getCardContent(Periodic.EVERYDAY_TASK)
        getCardContent(Periodic.WEEKLY_TASK)
    }

    private fun getCardContent(periodic: Periodic) {
        repository.getAllTasksFromPeriod(periodic)
            .subscribe( {
                val cardInfo = getScheduleCardInfo(it)
                // здесь может произойти ассинхронная ерунда
                // и не гарантируется порядок периодиков
                view?.setSchedulePeriodicCard(periodic, cardInfo)
            }, {
                Log.i("testTag", "error get All Tasks From Periodic")
            }
            )
    }

}