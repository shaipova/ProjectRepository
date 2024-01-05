package com.example.mytomatotrain.task.item

import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.mytomatotrain.R
import com.example.mytomatotrain.task.Tomato
import com.example.mytomatotrain.utils.convertMinutesInEstimatedTime
import com.example.mytomatotrain.utils.dp

class TaskItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    //private val container: ConstraintLayout = itemView.findViewById(R.id.item_task_container)
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

    // надо добавить цвет задачи

    fun setTaskColor(taskColor: Int) {
        //taskColorImage.setColorFilter(getColor(itemView.context, taskColor), PorterDuff.Mode.SRC_OVER)
    }

    fun setTomatoesAmount(listTomatoes: List<Tomato>) {
        // сюда надо будет добавить логику отрисовки томатов которые не полностью красные,
        // а уже пройдены полностью или частично
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

    fun setTaskTitle(title: String) {
        taskTitle.text = title
    }

    fun setOnButtonClickListener(listener: () -> Unit) {
        itemView.setOnClickListener {
            listener.invoke()
        }
    }

}