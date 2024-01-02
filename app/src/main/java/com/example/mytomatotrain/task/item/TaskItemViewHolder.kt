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


    fun setTaskColor(taskColor: Int) {
        taskColorImage.setColorFilter(getColor(itemView.context, taskColor), PorterDuff.Mode.SRC_OVER)
    }

    fun setTomatoesAmount(amount: Int) {
        repeat (amount) {
            val imageView = ImageView(itemView.context).apply {
                setImageResource(R.drawable.icon_tomato_10)
                setPadding(0, 0, 4.dp,0)
            }
            tomatoesLine.addView(imageView)
        }
        tomatoesEstimatedTime.text = amount.toString() // это не время, это число томатов (пока что)
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