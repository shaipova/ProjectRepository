package com.example.mytomatotrain

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core_api.database.DaoTask
import com.example.core_api.dto.Periodic
import com.example.core_api.dto.Status
import com.example.core_api.dto.Task
import com.example.core_api.dto.Tomato
import com.example.core_impl.TasksDatabase
import com.example.core_impl.TasksRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class TasksRepositoryTest {

    private lateinit var tasksDatabase: TasksDatabase
    private lateinit var tasksDao: DaoTask
    private lateinit var tasksRepositoryImpl: TasksRepositoryImpl

    private val taskEveryday = Task(
        id = 1,
        listTomatoes = mutableListOf(Tomato(durationInMin = 1, status = Status.CREATED)) ,
        title = "everyday",
        color = "violet_basic",
        periodic = Periodic.EVERYDAY_TASK
    )

    private val taskDone = Task(
        id = 2,
        listTomatoes = mutableListOf(Tomato(durationInMin = 1, status = Status.DONE)) ,
        title = "weekly",
        color = "violet_basic",
        periodic = Periodic.EVERYDAY_TASK
    )

    @Before
    fun setUpDatabase() {
        tasksDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TasksDatabase::class.java
        ).build()

        tasksDao = tasksDatabase.daoTask()
        tasksRepositoryImpl = TasksRepositoryImpl(tasksDatabase)
    }

    @Test
    fun testUpdateTaskByIdRepository() = runBlocking {
        tasksRepositoryImpl.upsertTask(taskEveryday)
        tasksRepositoryImpl.updateTaskById(taskEveryday.id, newTomatoStatus = Status.DONE, timeLeft = 0)

        val actual = tasksRepositoryImpl.isTaskByIdComplete(taskEveryday.id)
        assertEquals(true, actual)
    }

    @Test
    fun testRefreshTaskRepository() = runBlocking {
        tasksRepositoryImpl.upsertTask(taskDone)
        tasksRepositoryImpl.refreshTask(taskDone.id)

        val actual = tasksRepositoryImpl.isTaskByIdComplete(taskDone.id)
        val actualStatus = tasksRepositoryImpl.getTaskById(taskDone.id)?.listTomatoes?.first()?.status

        assertEquals(false, actual)
        assertEquals(Status.CREATED, actualStatus)
    }

    @Test
    fun testMarkTaskDoneRepository() = runBlocking {
        tasksRepositoryImpl.upsertTask(taskEveryday)
        tasksRepositoryImpl.markTaskDone(taskEveryday.id)

        val actual = tasksRepositoryImpl.isTaskByIdComplete(taskEveryday.id)
        val actualStatus = tasksRepositoryImpl.getTaskById(taskEveryday.id)?.listTomatoes?.first()?.status

        assertEquals(true, actual)
        assertEquals(Status.DONE, actualStatus)
    }

    @After
    fun tearDown() {
        tasksDatabase.close()
    }
}