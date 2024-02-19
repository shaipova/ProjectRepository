package com.example.mytomatotrain.task.item

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mytomatotrain.R
import com.example.mytomatotrain.task.Task
import com.example.mytomatotrain.task.Tomato
import com.example.mytomatotrain.utils.convertMinutesInEstimatedTime
import com.example.mytomatotrain.utils.dp
import com.example.mytomatotrain.utils.getColorResByName
import com.example.mytomatotrain.utils.setColor

class TaskItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val container: ConstraintLayout = itemView.findViewById(R.id.item_task_container)
    private val taskColorImage: ImageView = itemView.findViewById(R.id.item_task_color)
    private val taskTitle: TextView = itemView.findViewById(R.id.item_task_title)
    private val tomatoesLine: LinearLayout = itemView.findViewById(R.id.item_task_tomatoes_container)
    private val tomatoesEstimatedTime: TextView = itemView.findViewById(R.id.item_task_tomatoes_estimated_time)

    private val repeatButton: ImageButton = itemView.findViewById(R.id.item_repeat_button)
    private val pauseButton: ImageButton = itemView.findViewById(R.id.item_pause_button)
    private val startButton: ImageButton = itemView.findViewById(R.id.item_start_button)
    private val closeButton: ImageButton = itemView.findViewById(R.id.item_close_button)

    // этот виджет ещё должен обновляться на экране таймера каждую минуту с истечением времени
    // сделанные томаты должны закрашиваться
    // в прогрессе - закрашиваться на свой прогресс
    // эстимейтед тайм должно уменьшаться тоже

    fun bind(task: Task, callback: () -> Unit) {
        setTaskColor(getColorResByName(task.color))
        setTomatoesAmount(task.listTomatoes)
        setTaskTitle(task.title)
        itemView.setOnClickListener {
            callback.invoke()
        }
    }

    private fun setTaskColor(taskColor: Int) {
        taskColorImage.setColor(taskColor)
    }

    private fun setTomatoesAmount(listTomatoes: List<Tomato>) {
        // добавить логику отрисовки томатов в прогрессе и завершенных
        repeat (listTomatoes.size) {
            val imageView = ImageView(itemView.context).apply {
                setImageResource(R.drawable.icon_tomato_10)
                setPadding(0, 0, 4.dp,0)
            }
            tomatoesLine.addView(imageView)
        }
        val minutesLeft: Int = listTomatoes.sumOf { tomato ->
            tomato.timeLeft
        } / 60
        tomatoesEstimatedTime.text = convertMinutesInEstimatedTime(minutesLeft)
    }

    private fun setTaskTitle(title: String) {
        taskTitle.text = title
    }

}