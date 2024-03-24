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
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class TasksDatabaseTest {

    private lateinit var tasksDatabase: TasksDatabase
    private lateinit var tasksDao: DaoTask

    private val taskEveryday = Task(
        id = 1,
        listTomatoes = mutableListOf(Tomato(durationInMin = 1, status = Status.CREATED)) ,
        title = "everyday",
        color = "violet_basic",
        periodic = Periodic.EVERYDAY_TASK
    )

    private val taskEverydayUpdated = Task(
        id = 1,
        listTomatoes = mutableListOf(Tomato(durationInMin = 1, status = Status.DONE)) ,
        title = "everyday",
        color = "violet_basic",
        periodic = Periodic.EVERYDAY_TASK
    )

    private val taskWeekly = Task(
        id = 2,
        listTomatoes = mutableListOf(Tomato(durationInMin = 1, status = Status.CREATED)) ,
        title = "weekly",
        color = "violet_basic",
        periodic = Periodic.WEEKLY_TASK
    )

    @Before
    fun setUpDatabase() {
        tasksDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TasksDatabase::class.java
        ).build()

        tasksDao = tasksDatabase.daoTask()
    }

    @Test
    fun testUpsertTaskAndGetByName() = runBlocking {
        tasksDao.upsertTask(taskEveryday)

        val actual = tasksDao.getTaskByName(taskEveryday.title)

        assertEquals(taskEveryday, actual)
    }

    @Test
    fun testUpsertTaskAndGetById() = runBlocking {
        tasksDao.upsertTask(taskEveryday)

        val actual = tasksDao.getTaskById(taskEveryday.id)

        assertEquals(taskEveryday, actual)
    }

    @Test
    fun testDeleteTask() = runBlocking {
        tasksDao.upsertTask(taskEveryday)
        tasksDao.deleteTask(taskEveryday)

        val actual = tasksDao.getTaskByName(taskEveryday.title)

        assertEquals(null, actual)
    }

    @Test
    fun testGetAllTaskFromPeriod() = runBlocking {
        tasksDao.upsertTask(taskEveryday)
        tasksDao.upsertTask(taskWeekly)

        val actual = tasksDao.getAllTaskFromPeriod(Periodic.EVERYDAY_TASK)?.size

        assertEquals(1, actual)
    }

    @Test
    fun testGetAllTask() = runBlocking {
        tasksDao.upsertTask(taskEveryday)
        tasksDao.upsertTask(taskWeekly)

        val actual = tasksDao.getAllTask()?.size

        assertEquals(2, actual)
    }

    @Test
    fun testUpdateTask() = runBlocking {
        tasksDao.upsertTask(taskEveryday)

        taskEveryday.listTomatoes.map {
            it.status = Status.DONE
        }
        tasksDao.updateTask(taskEveryday)

        val actual = tasksDao.getTaskById(taskEveryday.id)
        assertEquals(taskEverydayUpdated, actual)
    }

    @After
    fun tearDown() {
        tasksDatabase.close()
    }
}