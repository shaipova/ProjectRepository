package com.example.mytomatotrain

import com.example.core_api.database.TasksRepository
import com.example.core_api.dto.Status
import com.example.core_api.dto.Task
import com.example.timer.TimerHelper
import kotlinx.coroutines.runBlocking
import android.content.Context
import com.example.core_api.dto.Periodic
import com.example.core_api.dto.Tomato
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class TimerHelperTest {

    @Mock
    lateinit var repository: TasksRepository

    @Mock
    lateinit var context: Context

    private val taskId = 1

    private val taskEveryday = Task(
        id = taskId,
        listTomatoes = mutableListOf(Tomato(durationInMin = 1, status = Status.CREATED)) ,
        title = "everyday",
        color = "violet_basic",
        periodic = Periodic.EVERYDAY_TASK
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetTask() = runBlocking {
        // Given
        val timerHelper = TimerHelper(context, repository)
        `when`(repository.getTaskById(taskId)).thenReturn(taskEveryday)

        // When
        val result = timerHelper.getTask(taskId)

        // Then
        verify(repository).getTaskById(taskId)
        assert(result == taskEveryday)
    }

    @Test
    fun testGetCurrentLeftTimeInSec() {
        // Given
        val timerHelper = TimerHelper(context, repository)
        val expectedTimeLeft = 60

        // When
        val result = timerHelper.getCurrentLeftTimeInSec(taskEveryday)

        // Then
        assert(result == expectedTimeLeft)
    }

    @Test
    fun testIsTaskComplete() = runBlocking {
        // Given
        val timerHelper = TimerHelper(context, repository)
        `when`(repository.isTaskByIdComplete(taskId)).thenReturn(true)

        // When
        val result = timerHelper.isTaskComplete(taskId)

        // Then
        verify(repository).isTaskByIdComplete(taskId)
        assert(result)
    }

    @Test
    fun testUpdateTaskInDB() = runBlocking {
        // Given
        val timerHelper = TimerHelper(context, repository)
        val status = Status.DONE
        val timeLeft = 0

        // When
        timerHelper.setTaskId(taskId)
        timerHelper.updateTaskInDB(status, timeLeft)

        // Then
        verify(repository).updateTaskById(taskId, status, timeLeft)
    }
}
