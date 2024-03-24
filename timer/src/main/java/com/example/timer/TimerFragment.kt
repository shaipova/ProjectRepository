package com.example.timer

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.base_util.Constants.TASK_KEY
import com.example.core_api.AppWithFacade
import com.example.core_api.dto.Status
import com.example.core_api.dto.Task
import javax.inject.Inject
import com.example.timer.di.TimerComponent
import kotlinx.coroutines.launch

interface TimerEventListener {
    fun onTimerEvent(event: TimerEvent)
}

class TimerFragment : Fragment(), TimerEventListener {

    @Inject
    lateinit var presenter: TimerPresenter

    @Inject
    lateinit var timerHelper: TimerHelper

    private var task: Task? = null

    companion object {
        fun newInstance() = TimerFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val timerComponent = TimerComponent.create(
            (requireActivity().application as AppWithFacade).getFacade()
        )
        timerComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.timer_view, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.timer_toolbar)
        val toolbarAction = toolbar.findViewById<ImageButton>(com.example.ui_core.R.id.toolbar_button_back)
        toolbarAction.setOnClickListener {
            requireActivity().onBackPressed()
        }


        task = arguments?.getSerializable(TASK_KEY) as? Task
        task?.let { timerHelper.setTaskId(it.id) }

        val fragmentView = TimerViewImpl(view)
        presenter.attachView(fragmentView)
        presenter.setHelper(timerHelper)
        presenter.setTimerEventListener(this)
        presenter.setContent(task)
        presenter.setListeners()

        timerHelper.setEventListenerToTimerWorker(this)
        lifecycleScope.launch {
            timerHelper.startTimerWorker { presenter.startTimer() }
        }
    }

    override fun onTimerEvent(event: TimerEvent) {
        when (event) {
            is TimerEvent.TimerTickEvent -> {
                presenter.updateTimer(event.timerValue)
            }
            is TimerEvent.TimerPauseEvent -> {
                lifecycleScope.launch {
                    timerHelper.stopTimerWorker()
                    event.timerValue?.let { timerHelper.updateTaskInDB(Status.PAUSED, it) }
                }

            }
            is TimerEvent.TimerStopEvent -> {
                lifecycleScope.launch {
                    timerHelper.updateTaskInDB(status = Status.CREATED, event.timerValue)
                    timerHelper.stopTimerWorker()
                    presenter.updateTimer(event.timerValue)
                }
            }
            is TimerEvent.TimerContinueEvent -> {
                lifecycleScope.launch {
                    event.timerValue?.let {
                        timerHelper.updateTaskInDB(Status.STARTED, it)
                    }
                    timerHelper.startTimerWorker {
                        presenter.startTimer()
                    }
                }

            }
            is TimerEvent.TimerStartEvent -> {
                lifecycleScope.launch {
                    timerHelper.startTimerWorker {
                        presenter.startTimer()
                    }
                }
            }
            is TimerEvent.TimerEndEvent -> {
                presenter.stopTimerAnimation()
                presenter.endTimer()
                timerHelper.stopTimerWorker()
                timerHelper.turnOnEndTomatoSound()
                lifecycleScope.launch {
                    timerHelper.updateTaskInDB(status = Status.DONE, timeLeft = 0)
                    val taskUpdated = task?.id?.let { timerHelper.getTask(it) }
                    presenter.setContent(taskUpdated)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}