package com.example.mytomatotrain.timer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mytomatotrain.Navigator
import com.example.mytomatotrain.R
import com.example.mytomatotrain.task.Task
import com.example.mytomatotrain.utils.Constants.TASK_KEY
import com.example.mytomatotrain.utils.Constants.TIMER_VALUE
import com.example.mytomatotrain.utils.Constants.WORK_NAME
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

interface TimerEventListener {
    fun onTimerEvent(event: TimerEvent)
}

class TimerFragment : Fragment(), TimerEventListener {

    private val presenter: TimerPresenter by inject()
    private var task: Task? = null
    private var currentTimerValue: Int? = null
    //lateinit var workManager: WorkManager

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
        presenter.setTask(task)
        presenter.setTimerEventListener(this)
        presenter.setContent()
        presenter.setListeners()

        //setWorkManager()
        TimerUpdateWorker.setListener(this)
        if (task != null) {
            val time = task!!.listTomatoes.size * 50 // просто тестовое значение чтобы проверить отображение
            startTimer(time)
        }
    }

//    private fun setWorkManager() {
//
//    }

    private fun startTimer(timerValue: Int) {
        val workManager = WorkManager.getInstance(requireContext())
        presenter.startTimer()

        val inputData = Data.Builder()
            .putInt(TIMER_VALUE, timerValue)
            .build()

        val timerWorkRequest = PeriodicWorkRequestBuilder<TimerUpdateWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.SECONDS
        )
            .setInputData(inputData)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            timerWorkRequest
        )
    }

//    private fun stopTimer() {
//        workManager.cancelUniqueWork(WORK_NAME)
//    }

    override fun onTimerEvent(event: TimerEvent) {
        when (event) {
            is TimerEvent.TimerCountingEvent -> {
                Log.i("testTag", "Timer Counting Event, event value = ${event.timerValue}")

                // приходит из воркера
                presenter.updateTimer(event.timerValue)
                currentTimerValue = event.timerValue
            }
            is TimerEvent.TimerPauseEvent -> {
                // приходит из презентера
                Log.i("testTag", "Timer Pause Event")
                currentTimerValue = event.timerValue //ну вот мы запомнили, как потом возобновить? сохранять в таймер хелпер в префсы?
                //stopTimer()
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}