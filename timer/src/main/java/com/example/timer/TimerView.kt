package com.example.timer

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.example.core_api.dto.InspirationalQuote
import com.example.core_api.dto.Task
import com.example.ui_core.ColorUtils
import com.example.ui_core.TaskItemViewHolder
import com.example.ui_core.timer_circles.TimerCircle
import com.example.ui_core.R as ui_R

interface TimerView {
    fun setTask(task: Task?)
    fun startTimer()
    fun stopTimer()
    fun pauseTimer()
    fun updateTimer(value: String)
    fun setQuote(quote: InspirationalQuote)

    fun stopAnimation()

    fun endTimer()

    fun setStartTimerButtonClickListener(listener: () -> Unit)
    fun setPauseTimerButtonClickListener(listener: () -> Unit)
    fun setContinueTimerButtonClickListener(listener: () -> Unit)
    fun setStopTimerButtonClickListener(listener: () -> Unit)
}

class TimerViewImpl(private val view: View): View(view.context), TimerView {

    private val timerCircle = view.findViewById<TimerCircle>(R.id.timer_circle)
    private val taskContainer = view.findViewById<FrameLayout>(R.id.timer_task_container)
    private val timerCount = view.findViewById<TextView>(R.id.timer_count)
    private val quoteText = view.findViewById<TextView>(R.id.quote_text)

    private val startTimerButton = view.findViewById<Button>(R.id.timer_start_button)
    private val pauseTimerButton = view.findViewById<Button>(R.id.timer_pause_button)
    private val continueTimerButton = view.findViewById<Button>(R.id.timer_continue_button)
    private val stopTimerButton = view.findViewById<Button>(R.id.timer_stop_button)


    override fun setTask(task: Task?) {
        taskContainer.removeAllViews()
        if (task == null) {
            setEmptyTaskScenario()
            return
        }
        val taskView = LayoutInflater.from(view.context).inflate(ui_R.layout.item_task_view, taskContainer)
        val taskViewHolder = TaskItemViewHolder(taskView)
        taskViewHolder.bind(task) {}
        setTimerColor(ColorUtils.getColorResByName(task.color))
    }

    override fun setQuote(quote: InspirationalQuote) {
        quoteText.text = quote.text
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

    override fun endTimer() {
        startTimerButton.visibility = VISIBLE
        continueTimerButton.visibility = GONE
        stopTimerButton.visibility = GONE
        pauseTimerButton.visibility = GONE
    }

    private fun setTimerColor(color: Int) {
        timerCircle.setCircleColor(color)
    }

    override fun setStartTimerButtonClickListener(listener: () -> Unit) {
        startTimerButton.setOnClickListener {
            listener()
        }
    }

    override fun setContinueTimerButtonClickListener(listener: () -> Unit) {
        continueTimerButton.setOnClickListener {
            listener()
        }
    }

    override fun setStopTimerButtonClickListener(listener: () -> Unit) {
        stopTimerButton.setOnClickListener {
            listener()
        }
    }

    override fun setPauseTimerButtonClickListener(listener: () -> Unit) {
        pauseTimerButton.setOnClickListener {
            listener()
        }
    }

    private fun setEmptyTaskScenario() {
        startTimerButton.visibility = VISIBLE
    }

}