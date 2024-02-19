package com.example.mytomatotrain.timer

import android.content.Context
import com.example.mytomatotrain.db.Repository
import com.example.mytomatotrain.task.Status
import com.example.mytomatotrain.task.Task
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class TimerHelper(context: Context, private val repository: Repository) {

    private var timerCounting = false
    private var startTime: Int? = null
    private var pauseTime: Int? = null
    private var task: Task? = null

    fun setTask(task: Task) {
        this.task = task
    }

    fun getTask(): Task? = task

    fun getCurrentLeftTimeInSec(id: Int): Single<Task> = repository.getTaskById(id)
    fun isTaskComplete(id: Int): Single<Boolean> = repository.isTaskByIdComplete(id)

    fun updateTaskInDB(status: Status, timeLeft: Int): Completable {
        val taskId = task?.id ?: return Completable.error(Throwable(message = "error update task, task id can not be null"))
        return repository.updateTaskById(
            id = taskId,
            newTomatoStatus = status,
            timeLeft = timeLeft
        )
    }
}