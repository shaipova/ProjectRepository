package com.example.ui_core

import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.core_api.dto.Task
import com.example.core_api.dto.Tomato
import com.example.base_util.convertMinutesInEstimatedTime
import com.example.base_util.dp
import com.example.base_util.setColor
import com.example.core_api.dto.Status
import com.example.ui_core.ColorUtils.getColorResByName

class TaskItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val container: ConstraintLayout = itemView.findViewById(R.id.item_task_container)
    private val taskColorImage: ImageView = itemView.findViewById(R.id.item_task_color)
    private val taskTitle: TextView = itemView.findViewById(R.id.item_task_title)
    private val tomatoesLine: LinearLayout = itemView.findViewById(R.id.item_task_tomatoes_container)
    private val tomatoesEstimatedTime: TextView = itemView.findViewById(R.id.item_task_tomatoes_estimated_time)

    private val repeatButton: ImageButton? = itemView.findViewById(R.id.item_repeat_button)

    // future features
//    private val pauseButton: ImageButton? = itemView.findViewById(R.id.item_pause_button)
//    private val startButton: ImageButton? = itemView.findViewById(R.id.item_start_button)
//    private val closeButton: ImageButton? = itemView.findViewById(R.id.item_close_button)

    fun setDialog(
        onDeleteClicked: () -> Unit,
        onMarkAsDoneClicked: (() -> Unit)?
    ) {
        container.setOnLongClickListener {
            showDialog(
                onDeleteClicked = onDeleteClicked,
                onMarkAsDoneClicked = onMarkAsDoneClicked
            )
            true
        }
    }

    private fun showDialog(
        onDeleteClicked: () -> Unit,
        onMarkAsDoneClicked: (() -> Unit)?
    ) {
        val builder = AlertDialog.Builder(itemView.context, R.style.ItemTaskDialog)
        val dialogView = LayoutInflater.from(itemView.context).inflate(R.layout.item_task_dialog, null)
        builder.setView(dialogView)
        val dialog = builder.create()

        dialogView.findViewById<TextView>(R.id.button_delete_task).setOnClickListener {
            onDeleteClicked()
            dialog.hide()
        }
        val markAsDoneButton = dialogView.findViewById<TextView>(R.id.button_mark_as_done)
        if (onMarkAsDoneClicked == null) {
            markAsDoneButton.visibility = GONE
        } else {
            markAsDoneButton.visibility = VISIBLE
            markAsDoneButton.setOnClickListener {
                onMarkAsDoneClicked()
                dialog.hide()
            }
        }

        dialog.show()
    }

    fun bind(task: Task, callback: () -> Unit) {
        setTaskColor(getColorResByName(task.color))
        setTomatoesAmount(task.listTomatoes)
        setTaskTitle(task.title)
        itemView.setOnClickListener {
            callback()
        }
    }

    fun setOnRepeatClickListener(callback: () -> Unit) {
        repeatButton?.setOnClickListener {
            callback()
        }
    }

    private fun setTaskColor(taskColor: Int) {
        taskColorImage.setColor(taskColor)
    }

    private fun setTomatoesAmount(listTomatoes: List<Tomato>) {
        val tomatoIcon = R.drawable.icon_tomato_10
        val tomatoDoneIcon = R.drawable.icon_done_tomato_10
        tomatoesLine.removeAllViews()

        listTomatoes.forEach {
            val isTomatoDone = it.status == Status.DONE
            val imageView = ImageView(itemView.context).apply {
                setImageResource(
                    if (isTomatoDone) tomatoDoneIcon else tomatoIcon
                )
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