package com.example.mytomatotrain.timer

import android.view.View
import com.example.mytomatotrain.TaskPresenter
import com.example.mytomatotrain.db.Repository
import com.example.mytomatotrain.task.FULL_TOMATO_TIME_IN_SEC_LARGE
import com.example.mytomatotrain.task.Status
import com.example.mytomatotrain.task.Task
import com.example.mytomatotrain.utils.convertSecondsInTimerFormat
import io.reactivex.rxjava3.core.Single

class TimerPresenter(private val repository: Repository) : TaskPresenter {

    private var view: TimerView? = null
    private var task: Task? = null
    private var timerEventListener: TimerEventListener? = null
    private var lastTimerValue: Int? = null

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

    fun setTimerColor(color: Int) {
        view?.setTimerColor(color)
    }

    fun updateTimer(timerValue: Int) {
        val timeFormat = convertSecondsInTimerFormat(timerValue)
        view?.updateTimer(timeFormat)
        lastTimerValue = timerValue
    }

    fun getCurrentLeftTimeInSec(id: Int): Single<Task> = repository.getTaskById(id)

    override fun setTask(task: Task?) {
        view?.setEmptyTaskScenario(task == null)
        this.task = task
    }

    override fun setListeners() {
        val taskId = task?.id ?: return

        view?.setPauseTimerButtonClickListener {
            lastTimerValue?.let { lastValue ->
                repository.updateTaskById(
                    id = taskId,
                    newTomatoStatus = Status.PAUSED,
                    timeLeft = lastValue
                ).subscribe({
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
                repository.updateTaskById(
                    id = taskId,
                    newTomatoStatus = Status.STARTED,
                    timeLeft = lastValue
                ).subscribe({
                    timerEventListener?.onTimerEvent(TimerEvent.TimerContinueEvent)
                }, {})
            }
        }
        view?.setStopTimerButtonClickListener {
            repository.updateTaskById(
                id = taskId,
                newTomatoStatus = Status.CREATED,
                timeLeft = FULL_TOMATO_TIME_IN_SEC_LARGE
            ).subscribe({
                timerEventListener?.onTimerEvent(TimerEvent.TimerStopEvent(FULL_TOMATO_TIME_IN_SEC_LARGE))
            }, {})
            view?.stopTimer()
        }
    }

    override fun detachView() {
        if (view != null) view = null
    }
}