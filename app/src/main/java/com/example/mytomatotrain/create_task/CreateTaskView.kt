package com.example.mytomatotrain.create_task

import android.annotation.SuppressLint
import android.text.Editable
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import com.example.mytomatotrain.R
import com.example.mytomatotrain.task.Periodic
import com.example.mytomatotrain.task.Task
import com.example.mytomatotrain.utils.SimpleTextWatcher
import com.example.mytomatotrain.utils.hideSystemKeyboard
import io.reactivex.rxjava3.core.Observable

interface CreateTaskView {
    fun observeTaskNameInput(listener: (Observable<String>) -> Unit)
    fun observeTomatoesAmountInput(listener: (Observable<String>) -> Unit)

    fun setOnDoneClick(listener: () -> Unit)
    fun setOnPeriodicButtonClickListener(listener: (Periodic) -> Unit)

    fun changeDoneButtonEnable(isEnabled: Boolean)

    fun displayTasks(list: List<Task>)
}

@SuppressLint("CheckResult")
class CreateTaskViewImpl(val view: View): View(view.context), CreateTaskView {

    //private var doneButton = view.findViewById<Button>(R.id.done_create_task_button)
    private val everydayButton = view.findViewById<RadioButton>(R.id.periodic_button_everyday)
    private val weeklyButton = view.findViewById<RadioButton>(R.id.periodic_button_weekly)

    private var taskNameInput = view.findViewById<EditText>(R.id.enter_task_name)
    private val tomatoesAmountInput = view.findViewById<EditText>(R.id.enter_tomatoes_amount)

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
        taskNameInput.setOnFocusChangeListener { view, hasFocus ->
            val name = (view as EditText).text.toString()
            if (!hasFocus) {
                listener(Observable.just(name))
                hideSystemKeyboard(view)
            }
        }
        taskNameInput.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                val name = s.toString()
                listener(Observable.just(name))
            }
        })
    }

    override fun observeTomatoesAmountInput(listener: (Observable<String>) -> Unit) {
        tomatoesAmountInput.setOnFocusChangeListener { view, hasFocus ->
            val tomatoes = (view as EditText).text.toString()
            if (!hasFocus) {
                listener(Observable.just(tomatoes))
                hideSystemKeyboard(view)
            }
        }
        tomatoesAmountInput.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                val tomatoes = s.toString()
                listener(Observable.just(tomatoes))
            }
        })
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
        listener.invoke()
        toolbarDoneButton.setOnClickListener {
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