package com.example.mytomatotrain.utils

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.Px
import com.example.mytomatotrain.task.ScheduleCardInfo
import com.example.mytomatotrain.task.Task

fun hideSystemKeyboard(view: View) {
    val inputMethodManager = view.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
val emptyScheduleCardInfo = ScheduleCardInfo("0 h 0 min", 0, 0)

fun getScheduleCardInfo(list: List<Task>?): ScheduleCardInfo {
    if (list.isNullOrEmpty()) return emptyScheduleCardInfo

    val uniquePeriodic = list.map { it.periodic }.distinct() // может эта проверка лишняя
    if (uniquePeriodic.size != 1) Log.i("testTag", "incorrect periodic type of list tasks")

    var estimatedTime = 0
    list.forEach { task ->
        val tomatoDuration = task.listTomatoes[0].durationInMin
        estimatedTime += task.listTomatoes.size * tomatoDuration
    }

    Log.i("testTag", "getScheduleCardInfo, list size = ${list.size}")
    val taskToDo = list.size
    val taskDone = list.filter { it.isTaskComplete }.size
    return ScheduleCardInfo(
        estimatedTime = convertMinutesInEstimatedTime(estimatedTime),
        taskToDo = taskToDo,
        taskDone = taskDone,
    )
}

fun convertMinutesInEstimatedTime(minutes: Int): String {
    val hours = minutes / 60
    val min = minutes % 60
    return "$hours h $min min"
}

fun convertSecondsInTimerFormat(seconds: Int): String {
    val min: Int = seconds / 60
    val sec: Int = seconds % 60

    val minPart = if(min < 10) "0$min" else "$min"
    val secPart = if(sec < 10) "0$sec" else "$sec"

    return "$minPart:$secPart"
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()