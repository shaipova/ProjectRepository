package com.example.mytomatotrain

import android.view.View
import com.example.mytomatotrain.task.Periodic
import com.example.mytomatotrain.task.Task
import com.example.mytomatotrain.timer.TimerEventListener

interface TaskPresenter {

    fun attachView(view: View)
    fun detachView()
    fun setContent()
    fun setPeriodic(periodic: Periodic) {}
    fun setTask(task: Task?) {}

    fun setListeners() {}

    fun startTimer() {}
    fun stopTimer() {}
    fun setTimerEventListener(eventListener: TimerEventListener) {}
}