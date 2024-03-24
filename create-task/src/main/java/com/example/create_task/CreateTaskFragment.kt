package com.example.create_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.core_api.AppWithFacade
import com.example.create_task.di.CreateTaskComponent
import com.example.schedule_api.ScheduleMediator
import javax.inject.Inject
import com.example.main_module.R as main_R

class CreateTaskFragment : Fragment() {

    @Inject
    lateinit var presenter: CreateTaskPresenter

    @Inject
    lateinit var scheduleMediator: ScheduleMediator

    companion object {
        fun newInstance() = CreateTaskFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val createTaskComponent = CreateTaskComponent.create(
            (requireActivity().application as AppWithFacade).getFacade()
        )
        createTaskComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_task_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.create_task_toolbar)
        val toolbarAction = toolbar.findViewById<ImageButton>(R.id.toolbar_button_close)

        toolbarAction.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val fragmentView = CreateTaskViewImpl(view)
        presenter.attachView(fragmentView)
        presenter.setListeners {
            scheduleMediator.openScheduleScreen(
                main_R.id.main_fragments_container,
                requireActivity().supportFragmentManager
            )
        }
        presenter.setContent(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.getState().run {
            outState.putString(TASK_NAME, getString(TASK_NAME))
            outState.putString(TASK_COLOR, getString(TASK_COLOR))
            outState.putString(TASK_PERIODIC, getString(TASK_PERIODIC))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

}

const val TASK_NAME = "task name"
const val TASK_PERIODIC = "task periodic"
const val TASK_COLOR = "task color"