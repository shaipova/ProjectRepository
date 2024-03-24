package com.example.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.base_util.Constants.PERIODIC_KEY
import com.example.core_api.AppWithFacade
import com.example.create_task_api.CreateTaskMediator
import com.example.schedule.di.ScheduleComponent
import com.example.time_period_schedule_api.TimePeriodScheduleMediator
import com.example.timer_api.TimerMediator
import javax.inject.Inject
import com.example.main_module.R as main_R

class ScheduleFragment : Fragment() {

    @Inject
    lateinit var presenter: SchedulePresenter

    @Inject
    lateinit var createTaskMediator: CreateTaskMediator

    @Inject
    lateinit var timePeriodScheduleMediator: TimePeriodScheduleMediator

    @Inject
    lateinit var timerMediator: TimerMediator

    companion object {
        fun newInstance() = ScheduleFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scheduleComponent = ScheduleComponent.create((requireActivity().application as AppWithFacade).getFacade())
        scheduleComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.schedule_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentView = ScheduleViewImpl(view)
        presenter.attachView(fragmentView)
        presenter.setListeners(
            addNewTaskListener = { createTaskMediator.openCreateTaskScreen(
                main_R.id.main_fragments_container,
                requireActivity().supportFragmentManager

            ) },
            startTaskListener = { timerMediator.openTimerScreen(
                main_R.id.main_fragments_container,
                requireActivity().supportFragmentManager,
                Bundle()
            )

            }
        )
        presenter.setContent(
            listener = { periodic ->
                timePeriodScheduleMediator.openTimePeriodScheduleScreen(
                    main_R.id.main_fragments_container,
                    requireActivity().supportFragmentManager,
                    Bundle().apply {
                        putSerializable(PERIODIC_KEY, periodic)
                    }
                )
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}