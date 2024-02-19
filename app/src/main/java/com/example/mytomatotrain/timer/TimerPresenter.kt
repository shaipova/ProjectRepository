package com.example.mytomatotrain.timer

import android.view.View
import com.example.mytomatotrain.TaskPresenter
import com.example.mytomatotrain.task.FULL_TOMATO_TIME_IN_SEC_LARGE
import com.example.mytomatotrain.task.Status
import com.example.mytomatotrain.utils.convertSecondsInTimerFormat

class TimerPresenter : TaskPresenter {

    private var view: TimerView? = null
    private var timerEventListener: TimerEventListener? = null
    private var lastTimerValue: Int? = null
    private lateinit var timerHelper: TimerHelper

    override fun attachView(view: View) {
        this.view = view as TimerView
    }

    override fun setContent() {
        view?.setTask(timerHelper.getTask())
    }

    override fun setHelper(timerHelper: TimerHelper) {
        this.timerHelper = timerHelper
    }

    override fun setTimerEventListener(eventListener: TimerEventListener) {
        this.timerEventListener = eventListener
    }

    override fun startTimer() {
        view?.startTimer()
    }

    fun setTimerColor(color: Int) {
        view?.setTimerColor(color)
    }

    fun updateTimer(timerValue: Int) {
        val timeFormat = convertSecondsInTimerFormat(timerValue)
        view?.updateTimer(timeFormat)
        lastTimerValue = timerValue
    }

    fun stopTimerAnimation() {
        view?.stopAnimation()
    }

    override fun setListeners() {
        view?.setPauseTimerButtonClickListener {
            lastTimerValue?.let { lastValue ->
                timerHelper.updateTaskInDB(status = Status.PAUSED, timeLeft = lastValue)
                    .subscribe({
                    timerEventListener?.onTimerEvent(TimerEvent.TimerPauseEvent)
                },{})
            }
            view?.pauseTimer()
        }
        view?.setStartTimerButtonClickListener {
            timerEventListener?.onTimerEvent(TimerEvent.TimerStartEvent)
        }
        view?.setContinueTimerButtonClickListener {
            lastTimerValue?.let { lastValue ->
                timerHelper.updateTaskInDB(status = Status.STARTED, timeLeft = lastValue)
                    .subscribe({
                    timerEventListener?.onTimerEvent(TimerEvent.TimerContinueEvent)
                }, {})
            }
        }
        view?.setStopTimerButtonClickListener {
            timerHelper.updateTaskInDB(status = Status.CREATED, timeLeft = FULL_TOMATO_TIME_IN_SEC_LARGE)
                .subscribe({
                timerEventListener?.onTimerEvent(TimerEvent.TimerStopEvent(FULL_TOMATO_TIME_IN_SEC_LARGE))
            }, {})
            view?.stopTimer()
        }
    }

    override fun detachView() {
        if (view != null) view = null
    }
}