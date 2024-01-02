package com.example.mytomatotrain.create_task

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import com.example.mytomatotrain.R
import com.example.mytomatotrain.task.Periodic
import com.example.mytomatotrain.task.Task
import com.example.mytomatotrain.utils.hideSystemKeyboard
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface CreateTaskView {
    fun observeTaskNameInput(listener: (Observable<String>) -> Unit)
    fun observeTomatoesAmountCounterChange(
        addTomatoListener: (Completable) -> Unit,
        removeTomatoListener: (Completable) -> Unit
    )
    fun changeSumTomatoTaskTime(time: String)

    fun setOnDoneClick(listener: () -> Unit)
    fun setOnPeriodicButtonClickListener(listener: (Periodic) -> Unit)

    fun changeDoneButtonEnable(isEnabled: Boolean)

    fun displayTasks(list: List<Task>)
}

@SuppressLint("CheckResult")
class CreateTaskViewImpl(val view: View) : View(view.context), CreateTaskView {

    private val everydayButton = view.findViewById<RadioButton>(R.id.periodic_button_everyday)
    private val weeklyButton = view.findViewById<RadioButton>(R.id.periodic_button_weekly)
    private var taskNameInput = view.findViewById<TaskNameInput>(R.id.task_name_input)
    private var tomatoesAmountCounter =
        view.findViewById<TomatoesAmountCounter>(R.id.tomatoes_amount_counter)
    private val toolbar = view.findViewById<Toolbar>(R.id.create_task_toolbar)
    private val toolbarDoneButton = toolbar.findViewById<TextView>(R.id.toolbar_done_button) // передать во вью и управлять через презентер


    init {
        view.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                performClick()
                val focusable = v.getFocusables(FOCUS_FORWARD)
                focusable.forEach {
                    it.clearFocus()
                }
            }
            false
        }
    }

    override fun observeTaskNameInput(listener: (Observable<String>) -> Unit) {
        taskNameInput.addTaskInputListeners(listener = listener)
    }

    override fun observeTomatoesAmountCounterChange(
        addTomatoListener: (Completable) -> Unit,
        removeTomatoListener: (Completable) -> Unit,
    ) {
        tomatoesAmountCounter.setOnAddTomatoClickListener(
            addTomatoListener = addTomatoListener,
            removeTomatoListener = removeTomatoListener

        )
    }

    override fun changeSumTomatoTaskTime(time: String) {
        tomatoesAmountCounter.changeSumTomatoTaskTime(time)
    }

    override fun setOnPeriodicButtonClickListener(listener: (Periodic) -> Unit) {
        everydayButton.setOnClickListener {
            listener(Periodic.EVERYDAY_TASK)
            hideSystemKeyboard(this)
        }
        weeklyButton.setOnClickListener {
            listener(Periodic.WEEKLY_TASK)
            hideSystemKeyboard(this)
        }
    }

    override fun setOnDoneClick(listener: () -> Unit) {
        toolbarDoneButton.setOnClickListener {
            listener()
            view.findNavController().navigate(R.id.action_createTaskFragment_to_scheduleFragment)
        }
    }

    override fun changeDoneButtonEnable(isEnabled: Boolean) {
        toolbarDoneButton.isEnabled = isEnabled
    }

    override fun displayTasks(list: List<Task>) {
        var str = ""
        list.forEach {
            str += it.title
            str += " "
        }
        Toast.makeText(context, "Задача $str успешно добавлена в список", Toast.LENGTH_LONG).show()
    }
}

const val EDIT_TEXT_DELAY = 700L