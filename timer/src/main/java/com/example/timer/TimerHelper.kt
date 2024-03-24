package com.example.timer

import android.content.Context
import android.media.MediaPlayer
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.base_util.Constants
import com.example.base_util.Constants.WORK_NAME
import com.example.core_api.database.TasksRepository
import com.example.core_api.dto.FULL_TOMATO_TIME_IN_SEC_LARGE
import com.example.core_api.dto.Status
import com.example.core_api.dto.Task
import javax.inject.Inject

class TimerHelper @Inject constructor(
    private val context: Context,
    private val repository: TasksRepository
) {

    private var taskId: Int? = null

    fun setEventListenerToTimerWorker(eventListener: TimerEventListener) {
        TimerUpdateWorker.setListener(eventListener)
    }

    suspend fun startTimerWorker(startListener: () -> Unit) {
        var timerValue = FULL_TOMATO_TIME_IN_SEC_LARGE
        if (taskId != null) {
            repository.getTaskById(taskId!!)?.let {
                timerValue = getCurrentLeftTimeInSec(it)
                startTimerWithTimerValue(timerValue)
                startListener()
            }
        } else {
            startTimerWithTimerValue(timerValue)
            startListener()
        }
    }

    private fun startTimerWithTimerValue(timerValue: Int) {
        val inputData = Data.Builder()
            .putInt(Constants.TIMER_VALUE, timerValue)
            .build()

        val timerWorkRequest = OneTimeWorkRequestBuilder<TimerUpdateWorker>()
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            timerWorkRequest
        )
    }

    fun stopTimerWorker() {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }

    fun turnOnEndTomatoSound() {
        val mediaPlayer = MediaPlayer.create(context, R.raw.kalimba_c4_2s)
        if (mediaPlayer != null && !mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    fun setTaskId(id: Int) {
        taskId = id
    }

    suspend fun getTask(id: Int) = repository.getTaskById(id)

    fun getCurrentLeftTimeInSec(task: Task): Int {
        return task.listTomatoes.findLast {
            it.status != Status.DONE
        }?.timeLeft ?: 0
    }

    suspend fun isTaskComplete(id: Int): Boolean = repository.isTaskByIdComplete(id)

    suspend fun updateTaskInDB(status: Status, timeLeft: Int) {
        val taskId = taskId ?: return
        repository.updateTaskById(
            id = taskId,
            newTomatoStatus = status,
            timeLeft = timeLeft
        )
    }
}