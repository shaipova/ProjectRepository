package com.example.base_util

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.Resources
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.core_api.dto.ScheduleCardInfo
import com.example.core_api.dto.Task

fun hideSystemKeyboard(view: View) {
    val inputMethodManager = view.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
val emptyScheduleCardInfo = ScheduleCardInfo("0 h 0 min", 0, 0)

fun getScheduleCardInfoForPeriodic(list: List<Task>?): ScheduleCardInfo {
    if (list.isNullOrEmpty()) return emptyScheduleCardInfo

    val uniquePeriodic = list.map { it.periodic }.distinct()
    if (uniquePeriodic.size != 1) {
        throw Exception("Incorrect periodic type of list tasks")
    }

    var estimatedTime = 0
    list.forEach { task ->
        val tomatoDuration = task.listTomatoes[0].durationInMin
        estimatedTime += task.listTomatoes.size * tomatoDuration
    }

    val taskDone = list.filter { it.isTaskComplete }.size
    val taskToDo = list.size - taskDone
    return ScheduleCardInfo(
        estimatedTime = convertMinutesInEstimatedTime(estimatedTime),
        taskToDo = taskToDo,
        taskDone = taskDone,
    )
}

fun View.changeVisibility(shouldShow: Boolean) {
    if (shouldShow) {
        this.visibility = VISIBLE
    } else {
        this.visibility = GONE
    }
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

fun ImageView.setColor(color: Int) {
    this.setColorFilter(ContextCompat.getColor(this.context, color))
}