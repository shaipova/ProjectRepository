package com.example.core_impl

import com.example.core_api.database.TasksDatabaseContract
import com.example.core_api.database.TasksRepository
import com.example.core_api.dto.FULL_TOMATO_TIME_IN_SEC_LARGE
import javax.inject.Inject
import com.example.core_api.dto.Periodic
import com.example.core_api.dto.Status
import com.example.core_api.dto.Task

class TasksRepositoryImpl @Inject constructor(
    tasksDatabase: TasksDatabaseContract
) : TasksRepository {

    private val dao = tasksDatabase.daoTask()

    override suspend fun upsertTask(task: Task) = dao.upsertTask(task)

    override suspend fun deleteTask(task: Task) = dao.deleteTask(task)

    override suspend fun getTaskByName(title: String): Task? = dao.getTaskByName(title)

    override suspend fun getAllTasksFromPeriod(periodic: Periodic): List<Task>? = dao.getAllTaskFromPeriod(periodic)

    override suspend fun getAllTasks(): List<Task>? = dao.getAllTask()

    override suspend fun getTaskById(id: Int): Task? = dao.getTaskById(id)

    override suspend fun isTaskByIdComplete(id: Int): Boolean = dao.getTaskById(id)?.isTaskComplete == true

    override suspend fun updateTaskById(id: Int, newTomatoStatus: Status, timeLeft: Int) {
        val task = dao.getTaskById(id)
        if (task != null && task.listTomatoes.isNotEmpty()) {
            val lastTomato = task.listTomatoes.findLast {
                it.status != Status.DONE
            }
            lastTomato?.status = newTomatoStatus
            lastTomato?.timeLeft = timeLeft
            dao.updateTask(task)
        }
    }

    override suspend fun refreshTask(id: Int) {
        val task = dao.getTaskById(id)
        if (task != null) {
            task.listTomatoes.map {
                it.status = Status.CREATED
                it.timeLeft = FULL_TOMATO_TIME_IN_SEC_LARGE
            }
            dao.updateTask(task)
        }
    }

    override suspend fun markTaskDone(id: Int) {
        val task = dao.getTaskById(id)
        if (task != null) {
            task.listTomatoes.map {
                it.status = Status.DONE
                it.timeLeft = 0
            }
            dao.updateTask(task)
        }
    }
}