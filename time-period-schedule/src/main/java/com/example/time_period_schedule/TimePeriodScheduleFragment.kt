package com.example.time_period_schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.base_util.Constants
import com.example.base_util.Constants.PERIODIC_KEY
import com.example.core_api.AppWithFacade
import com.example.core_api.dto.Periodic
import com.example.create_task_api.CreateTaskMediator
import com.example.time_period_schedule.di.TimePeriodScheduleComponent
import com.example.timer_api.TimerMediator
import javax.inject.Inject
import com.example.main_module.R as main_R
import com.example.ui_core.R as ui_R

class TimePeriodScheduleFragment : Fragment() {

    @Inject
    lateinit var presenter: TimePeriodSchedulePresenter

    @Inject
    lateinit var createTaskMediator: CreateTaskMediator

    @Inject
    lateinit var timerMediator: TimerMediator

    companion object {
        fun newInstance() = TimePeriodScheduleFragment()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val timePeriodScheduleComponent = TimePeriodScheduleComponent.create(
            (requireActivity().application as AppWithFacade).getFacade()
        )
        timePeriodScheduleComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.period_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val periodic = arguments?.getSerializable(PERIODIC_KEY) as? Periodic

        val toolbar = view.findViewById<Toolbar>(R.id.periodic_toolbar)
        val toolbarTitle = toolbar.findViewById<TextView>(ui_R.id.toolbar_title)
        val toolbarAction = toolbar.findViewById<ImageButton>(ui_R.id.toolbar_button_back)

        toolbarTitle.text = periodic?.title
        toolbarAction.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val fragmentView = TimePeriodScheduleViewImpl(view)
        presenter.attachView(fragmentView)
        presenter.setPeriodic(periodic)
        presenter.setListeners(
            addNewTaskListener = {
                createTaskMediator.openCreateTaskScreen(
                    main_R.id.main_fragments_container,
                    requireActivity().supportFragmentManager

                )
            },
            startTimerListener = { task ->
                timerMediator.openTimerScreen(
                    main_R.id.main_fragments_container,
                    requireActivity().supportFragmentManager,
                    Bundle().apply {
                        putSerializable(Constants.TASK_KEY, task)
                    }
                )
            }
        )
        presenter.setContent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}