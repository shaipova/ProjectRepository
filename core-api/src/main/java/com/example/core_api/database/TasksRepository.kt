package com.example.core_api.database

import com.example.core_api.dto.Periodic
import com.example.core_api.dto.Status
import com.example.core_api.dto.Task

interface TasksRepository {

    suspend fun upsertTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun getTaskByName(title: String): Task?
    suspend fun getAllTasksFromPeriod(periodic: Periodic): List<Task>?
    suspend fun getAllTasks(): List<Task>?
    suspend fun updateTaskById(id: Int, newTomatoStatus: Status, timeLeft: Int)
    suspend fun getTaskById(id: Int): Task?
    suspend fun isTaskByIdComplete(id: Int): Boolean

    suspend fun refreshTask(id: Int)

    suspend fun markTaskDone(id: Int)

}