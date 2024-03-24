package com.example.create_task

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.create_task.color_picker.ColorPickerListAdapter
import com.example.create_task.color_picker.GridLayoutDecoration
import com.example.core_api.dto.Periodic
import com.example.core_api.dto.Task
import com.example.base_util.dp
import com.example.base_util.hideSystemKeyboard
import com.example.core_api.dto.ColorItem
import com.example.ui_core.ColorUtils.getColorResByName
import kotlinx.coroutines.flow.Flow

interface CreateTaskView {
    fun setContent(name: String, periodic: Periodic?, color: String)
    fun observeTaskNameInput(): Flow<String>
    fun observeTomatoesAmountCounterChange(
        addTomatoListener: () -> Unit,
        removeTomatoListener: () -> Unit
    )

    fun changeSumTomatoTaskTime(time: String)

    fun setOnDoneClick(listener: () -> Unit)
    fun setOnPeriodicButtonClickListener(listener: (Periodic) -> Unit)

    fun changeDoneButtonEnable(isEnabled: Boolean)

    fun displayTask(task: Task)

    fun setColorAdapter(list: List<ColorItem>, callback: ColorPickerAdapterCallback)

    fun updateColorPicker(list: List<ColorItem>)
    fun updateTaskNameInputColor(color: Int)
}

@SuppressLint("CheckResult")
class CreateTaskViewImpl(view: View) : View(view.context), CreateTaskView {

    private val everydayButton = view.findViewById<RadioButton>(R.id.periodic_button_everyday)
    private val weeklyButton = view.findViewById<RadioButton>(R.id.periodic_button_weekly)
    private var taskNameInput = view.findViewById<TaskNameInput>(R.id.task_name_input)
    private var tomatoesAmountCounter =
        view.findViewById<TomatoesAmountCounter>(R.id.tomatoes_amount_counter)
    private val toolbar = view.findViewById<Toolbar>(R.id.create_task_toolbar)
    private val toolbarDoneButton = toolbar.findViewById<TextView>(R.id.toolbar_done_button)
    private val colorPickerGrid = view.findViewById<RecyclerView>(R.id.color_picker_grid)
    private lateinit var adapter: ColorPickerListAdapter

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

    override fun setContent(
        name: String,
        periodic: Periodic?,
        color: String
    ) {
        taskNameInput.setTaskColor(getColorResByName(color))
        taskNameInput.setText(name)
        if (periodic == Periodic.EVERYDAY_TASK) {
            everydayButton.isChecked = true
        } else {
            weeklyButton.isChecked = true
        }
        colorPickerGrid
    }

    override fun observeTaskNameInput() = taskNameInput.addTaskInputListeners()

    override fun observeTomatoesAmountCounterChange(
        addTomatoListener: () -> Unit,
        removeTomatoListener: () -> Unit,
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
        }
    }

    override fun changeDoneButtonEnable(isEnabled: Boolean) {
        toolbarDoneButton.isEnabled = isEnabled
    }

    override fun displayTask(task: Task) {
        Toast.makeText(context, "Task ${task.title} successfully added to the list", Toast.LENGTH_LONG).show()
    }

    override fun setColorAdapter(list: List<ColorItem>, callback: ColorPickerAdapterCallback) {
        adapter = ColorPickerListAdapter(callback)
        colorPickerGrid.adapter = adapter
        colorPickerGrid.addItemDecoration(GridLayoutDecoration(6, 24.dp))
        adapter.submitList(list)
        adapter.notifyDataSetChanged()
    }

    override fun updateColorPicker(list: List<ColorItem>) {
        adapter.submitList(list)
        adapter.notifyDataSetChanged()
    }

    override fun updateTaskNameInputColor(color: Int) {
        taskNameInput.setTaskColor(color)
    }
}

const val EDIT_TEXT_DELAY = 700L