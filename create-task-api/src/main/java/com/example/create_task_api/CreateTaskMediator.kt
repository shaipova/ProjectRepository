package com.example.create_task_api

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

interface CreateTaskMediator {
    fun openCreateTaskScreen(@IdRes containerId: Int, fragmentManager: FragmentManager)
}