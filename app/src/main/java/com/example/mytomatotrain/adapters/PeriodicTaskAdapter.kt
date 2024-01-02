package com.example.mytomatotrain.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytomatotrain.R
import com.example.mytomatotrain.task.Task
import com.example.mytomatotrain.task.item.TaskItemViewHolder
import com.example.mytomatotrain.time_period.AdapterCallback

class PeriodicTaskAdapter(private val callback: AdapterCallback) : RecyclerView.Adapter<TaskItemViewHolder>() {

    lateinit var taskList: MutableList<Task>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task_view, parent, false)
        return TaskItemViewHolder(view)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = taskList[position]
        holder.apply {
            setTaskColor(R.color.cyan) // test

            //setTaskColor(item.color)
            setTomatoesAmount(item.listTomatoes.size)
            setTaskTitle(item.title)
            setOnButtonClickListener {
                callback.onTaskClicked(item)
            }
        }
    }
}