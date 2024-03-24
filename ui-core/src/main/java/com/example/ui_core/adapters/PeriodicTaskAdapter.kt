package com.example.ui_core.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.base_util.AdapterCallback
import com.example.core_api.dto.Task
import com.example.ui_core.R
import com.example.ui_core.TaskItemViewHolder

class PeriodicTaskListAdapter(private val callback: AdapterCallback) : ListAdapter<Task, TaskItemViewHolder>(
    PeriodicItemDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task_view, parent, false)
        return TaskItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item) {
            callback.onTaskClicked(item)
        }
        holder.setDialog(
            onDeleteClicked = { callback.onDeleteClicked(item) },
            onMarkAsDoneClicked = { callback.onMarkAsDoneClicked(item) }
        )
    }
}