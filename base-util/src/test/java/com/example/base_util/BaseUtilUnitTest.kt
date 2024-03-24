package com.example.base_util

import com.example.core_api.dto.Periodic
import com.example.core_api.dto.ScheduleCardInfo
import com.example.core_api.dto.Status
import com.example.core_api.dto.Task
import com.example.core_api.dto.Tomato
import org.junit.Test

import org.junit.Assert.*
import kotlin.random.Random

class BaseUtilUnitTest {
    @Test
    fun testConvertMinutesInEstimatedTime() {
        assertEquals("2 h 30 min", convertMinutesInEstimatedTime(150))
        assertEquals("1 h 0 min", convertMinutesInEstimatedTime(60))
        assertEquals("0 h 45 min", convertMinutesInEstimatedTime(45))
        assertEquals("0 h 0 min", convertMinutesInEstimatedTime(0))
    }

    @Test
    fun testConvertSecondsInTimerFormat() {
        assertEquals("02:30", convertSecondsInTimerFormat(150))
        assertEquals("01:00", convertSecondsInTimerFormat(60))
        assertEquals("00:45", convertSecondsInTimerFormat(45))
        assertEquals("00:00", convertSecondsInTimerFormat(0))
    }

    private fun getSimpleTask(periodic: Periodic, isComplete: Boolean): Task {
        return Task(
            id = Random.nextInt(50),
            listTomatoes = mutableListOf(getSimpleTomato(isComplete), getSimpleTomato(isComplete)) ,
            title = "title",
            color = "violet_basic",
            periodic = periodic
        )
    }

    private fun getSimpleTomato(isComplete: Boolean): Tomato {
        val status = if (isComplete) Status.DONE else Status.CREATED
        return Tomato(durationInMin = 1, status = status)
    }

    @Test
    fun testGetScheduleCardInfoForPeriodic() {
        val taskEveryDayComplete = getSimpleTask(Periodic.EVERYDAY_TASK, isComplete = true)
        val taskEveryDayTodo = getSimpleTask(Periodic.EVERYDAY_TASK, isComplete = false)
        val taskEveryDayToDo2 = getSimpleTask(Periodic.EVERYDAY_TASK, isComplete = false)

        val taskWeeklyComplete = getSimpleTask(Periodic.WEEKLY_TASK, isComplete = true)
        val taskWeeklyTodo = getSimpleTask(Periodic.WEEKLY_TASK, isComplete = false)
        val taskWeeklyToDo2 = getSimpleTask(Periodic.WEEKLY_TASK, isComplete = false)

        val listEveryDay = listOf(taskEveryDayComplete, taskEveryDayTodo, taskEveryDayToDo2)
        val listWeekly = listOf(taskWeeklyComplete, taskWeeklyTodo, taskWeeklyToDo2)

        val expectedInfoEveryDaySchedule = ScheduleCardInfo("0 h 6 min", 2, 1)
        val expectedInfoWeeklySchedule = ScheduleCardInfo("0 h 6 min", 2, 1)

        assertEquals(expectedInfoEveryDaySchedule, getScheduleCardInfoForPeriodic(listEveryDay))
        assertEquals(expectedInfoWeeklySchedule, getScheduleCardInfoForPeriodic(listWeekly))
    }

    @Test
    fun testGetScheduleCardInfoWhenEmptyList() {
        val emptyList = emptyList<Task>()
        val emptyScheduleCardInfo = ScheduleCardInfo("0 h 0 min", 0, 0)

        assertEquals(emptyScheduleCardInfo, getScheduleCardInfoForPeriodic(emptyList))
    }

    @Test
    fun testGetScheduleCardInfoWhenDifferentPeriodicType() {
        val taskEveryDayComplete = getSimpleTask(Periodic.EVERYDAY_TASK, isComplete = true)
        val taskWeeklyComplete = getSimpleTask(Periodic.WEEKLY_TASK, isComplete = true)

        val list = listOf(taskEveryDayComplete, taskWeeklyComplete)

        val exception = assertThrows(Exception::class.java) {
            getScheduleCardInfoForPeriodic(list)
        }
        assertEquals("incorrect periodic type of list tasks", exception.message)
    }
}