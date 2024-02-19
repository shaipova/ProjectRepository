package com.example.mytomatotrain

import android.view.View
import com.example.mytomatotrain.task.Periodic
import com.example.mytomatotrain.timer.TimerEventListener
import com.example.mytomatotrain.timer.TimerHelper

interface TaskPresenter {

    fun attachView(view: View)
    fun detachView()
    fun setContent()
    fun setPeriodic(periodic: Periodic) {}
    fun setHelper(timerHelper: TimerHelper) {}

    fun setListeners() {}

    fun startTimer() {}
    fun stopTimer() {}
    fun setTimerEventListener(eventListener: TimerEventListener) {}
}