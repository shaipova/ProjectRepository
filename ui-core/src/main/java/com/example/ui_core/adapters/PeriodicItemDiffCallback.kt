package com.example.ui_core.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.core_api.dto.Task

class PeriodicItemDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return when {
            oldItem.id == newItem.id -> true
            oldItem.title == newItem.title -> true
            oldItem.color == newItem.color -> true
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return when {
            oldItem.isTaskComplete == newItem.isTaskComplete -> true
            oldItem.listTomatoes.size == newItem.listTomatoes.size -> true
            else -> false
        }
    }
}