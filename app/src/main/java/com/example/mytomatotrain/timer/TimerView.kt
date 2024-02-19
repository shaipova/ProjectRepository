package com.example.mytomatotrain.timer

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.example.mytomatotrain.R
import com.example.mytomatotrain.task.Task
import com.example.mytomatotrain.task.item.TaskItemViewHolder
import com.example.mytomatotrain.timer_circles.TimerCircle

interface TimerView {
    fun setTask(task: Task?)
    fun startTimer()
    fun stopTimer()
    fun pauseTimer()
    fun updateTimer(value: String)

    fun stopAnimation()

    fun setTimerColor(color: Int)

    fun setSelectTaskButtonClickListener(listener: () -> Unit)
    fun setStartTimerButtonClickListener(listener: () -> Unit)
    fun setPauseTimerButtonClickListener(listener: () -> Unit)
    fun setContinueTimerButtonClickListener(listener: () -> Unit)
    fun setStopTimerButtonClickListener(listener: () -> Unit)
}

class TimerViewImpl(val view: View): View(view.context), TimerView {

    private val selectTaskButton = view.findViewById<Button>(R.id.timer_select_a_task_button)
    private val timerCircle = view.findViewById<TimerCircle>(R.id.timer_circle)
    private val taskContainer = view.findViewById<FrameLayout>(R.id.timer_task_container)
    private val timerCount = view.findViewById<TextView>(R.id.timer_count)

    private val startTimerButton = view.findViewById<Button>(R.id.timer_start_button)
    private val pauseTimerButton = view.findViewById<Button>(R.id.timer_pause_button)
    private val continueTimerButton = view.findViewById<Button>(R.id.timer_continue_button)
    private val stopTimerButton = view.findViewById<Button>(R.id.timer_stop_button)


    override fun setTask(task: Task?) {
        if (task == null) {
            setEmptyTaskScenario()
            return
        }
        val taskView = LayoutInflater.from(view.context).inflate(R.layout.item_task_view, taskContainer)
        val taskViewHolder = TaskItemViewHolder(taskView)
        taskViewHolder.bind(task) {}
    }

    override fun startTimer() {
        timerCircle.startAnimation()
        pauseTimerButton.visibility = VISIBLE
        startTimerButton.visibility = GONE
        continueTimerButton.visibility = GONE
        stopTimerButton.visibility = GONE
    }

    override fun stopTimer() {
        timerCircle.stopAnimation()
        continueTimerButton.visibility = GONE
        stopTimerButton.visibility = GONE
        pauseTimerButton.visibility = GONE
        startTimerButton.visibility = VISIBLE

    }

    override fun pauseTimer() {
        timerCircle.stopAnimation()
        continueTimerButton.visibility = VISIBLE
        stopTimerButton.visibility = VISIBLE
        startTimerButton.visibility = GONE
        pauseTimerButton.visibility = GONE
    }

    override fun updateTimer(value: String) {
        timerCount.text = value
    }

    override fun stopAnimation() {
        timerCircle.stopAnimation()
    }

    override fun setTimerColor(color: Int) {
        timerCircle.setCircleColor(color)
    }

    override fun setSelectTaskButtonClickListener(listener: () -> Unit) {
        //
    }

    override fun setStartTimerButtonClickListener(listener: () -> Unit) {
        startTimerButton.setOnClickListener {
            listener.invoke()
        }
    }

    override fun setContinueTimerButtonClickListener(listener: () -> Unit) {
        continueTimerButton.setOnClickListener {
            listener.invoke()
        }
    }

    override fun setStopTimerButtonClickListener(listener: () -> Unit) {
        stopTimerButton.setOnClickListener {
            listener.invoke()
        }
    }

    override fun setPauseTimerButtonClickListener(listener: () -> Unit) {
        pauseTimerButton.setOnClickListener {
            listener.invoke()
        }
    }

    private fun setEmptyTaskScenario() {
        selectTaskButton.visibility = VISIBLE
        startTimerButton.visibility = VISIBLE
    }

}