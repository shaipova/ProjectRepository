package com.example.mytomatotrain.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.mytomatotrain.create_task.color_picker.ColorItem
import com.example.mytomatotrain.task.Task

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
            oldItem.listTomatoes == newItem.listTomatoes -> true
            else -> false
        }
    }
}

class ColorPickerItemDiffCallback : DiffUtil.ItemCallback<ColorItem>() {
    override fun areItemsTheSame(oldItem: ColorItem, newItem: ColorItem): Boolean =
        oldItem.colorResName == newItem.colorResName

    override fun areContentsTheSame(oldItem: ColorItem, newItem: ColorItem): Boolean =
        oldItem.isSelected == newItem.isSelected
}