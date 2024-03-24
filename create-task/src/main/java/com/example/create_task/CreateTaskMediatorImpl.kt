package com.example.create_task

import androidx.fragment.app.FragmentManager
import com.example.create_task_api.CreateTaskMediator
import javax.inject.Inject

class CreateTaskMediatorImpl @Inject constructor() : CreateTaskMediator {
    override fun openCreateTaskScreen(containerId: Int, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(containerId, CreateTaskFragment.newInstance())
            .commit()
    }
}