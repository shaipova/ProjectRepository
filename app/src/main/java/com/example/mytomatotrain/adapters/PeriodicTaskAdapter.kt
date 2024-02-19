package com.example.mytomatotrain.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.mytomatotrain.R
import com.example.mytomatotrain.task.Task
import com.example.mytomatotrain.task.item.TaskItemViewHolder
import com.example.mytomatotrain.time_period.AdapterCallback

class PeriodicTaskListAdapter(private val callback: AdapterCallback) : ListAdapter<Task, TaskItemViewHolder>(PeriodicItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task_view, parent, false)
        return TaskItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item) {
            callback.onTaskClicked(item)
        }
    }
}