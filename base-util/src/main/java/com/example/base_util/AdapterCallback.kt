package com.example.base_util

import com.example.core_api.dto.Task

interface AdapterCallback {
    fun onTaskClicked(task: Task)
    fun onDoneTaskClicked(task: Task)
    fun onDeleteClicked(task: Task)
    fun onMarkAsDoneClicked(task: Task)
}