package com.example.mytomatotrain.timer

import android.view.View
import com.example.mytomatotrain.TaskPresenter
import com.example.mytomatotrain.db.Repository
import com.example.mytomatotrain.task.Task
import com.example.mytomatotrain.utils.convertSecondsInTimerFormat

class TimerPresenter(private val repository: Repository): TaskPresenter {

    private var view: TimerView? = null
    private var task: Task? = null
    private var timerEventListener: TimerEventListener? = null

    override fun attachView(view: View) {
        this.view = view as TimerView
    }

    override fun setContent() {
        view?.setTask(task)
    }

    override fun setTimerEventListener(eventListener: TimerEventListener) {
        this.timerEventListener = eventListener
    }

    override fun startTimer() {
        view?.startTimer()
    }

    fun updateTimer(timerValue: Int) {
        val timeFormat = convertSecondsInTimerFormat(timerValue)
        view?.updateTimer(timeFormat)
    }

    override fun setTask(task: Task?) {
        view?.setEmptyTaskScenario(task == null)
        this.task = task
    }

    override fun setListeners() {
        view?.setPauseTimerButtonClickListener {
            //timerEventListener?.onTimerEvent(TimerEvent.TimerPauseEvent())
        }
        view?.setStartTimerButtonClickListener {
            //timerEventListener?.onTimerEvent()
        }
    }

    override fun detachView() {
        if (view != null) view = null
    }
}