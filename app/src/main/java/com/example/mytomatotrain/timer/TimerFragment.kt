package com.example.mytomatotrain.timer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mytomatotrain.R
import com.example.mytomatotrain.task.FULL_TOMATO_TIME_IN_SEC_LARGE
import com.example.mytomatotrain.task.Task
import com.example.mytomatotrain.utils.Constants.TASK_KEY
import com.example.mytomatotrain.utils.Constants.TIMER_VALUE
import com.example.mytomatotrain.utils.Constants.WORK_NAME
import com.example.mytomatotrain.utils.getColorResByName
import org.koin.android.ext.android.inject

interface TimerEventListener {
    fun onTimerEvent(event: TimerEvent)
}

class TimerFragment : Fragment(), TimerEventListener {

    private val presenter: TimerPresenter by inject()
    private var task: Task? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.timer_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        task = arguments?.getSerializable(TASK_KEY) as? Task

        val fragmentView = TimerViewImpl(view)
        presenter.attachView(fragmentView)
        task?.let {presenter.setTask(it) }
        presenter.setTimerEventListener(this)
        presenter.setContent()
        presenter.setListeners()
        presenter.setTimerColor(getColorResByName(task?.color))

        TimerUpdateWorker.setListener(this)
        startTimer()
    }

    private fun startTimer() {
        var timerValue = FULL_TOMATO_TIME_IN_SEC_LARGE
        if (task != null) {
            presenter.getCurrentLeftTimeInSec(task!!.id).subscribe(
                {
                    timerValue = it.listTomatoes.last().timeLeft
                    Log.i("testTag", "timerValue = $timerValue")
                    startTimerWithTimerValue(timerValue)
                },{ throwable ->
                    Log.i("testTag", "error: ${throwable.message}")
                }
            )
        } else {
            startTimerWithTimerValue(timerValue)
        }

    }

    private fun startTimerWithTimerValue(timerValue: Int) {
        presenter.startTimer()

        val inputData = Data.Builder()
            .putInt(TIMER_VALUE, timerValue)
            .build()

        val timerWorkRequest = OneTimeWorkRequestBuilder<TimerUpdateWorker>()
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(requireContext()).enqueueUniqueWork(
            WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            timerWorkRequest
        )
    }

    private fun stopTimer() {
        WorkManager.getInstance(requireContext()).cancelUniqueWork(WORK_NAME)
    }

    override fun onTimerEvent(event: TimerEvent) {
        when (event) {
            is TimerEvent.TimerTickEvent -> presenter.updateTimer(event.timerValue) // from Worker
            is TimerEvent.TimerPauseEvent -> stopTimer()
            is TimerEvent.TimerStopEvent -> {
                stopTimer()
                presenter.updateTimer(event.timerValue)
            }
            is TimerEvent.TimerContinueEvent -> startTimer()
            is TimerEvent.TimerStartEvent -> startTimer()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}