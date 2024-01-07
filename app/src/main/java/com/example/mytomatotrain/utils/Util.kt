package com.example.mytomatotrain.utils

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.mytomatotrain.R
import com.example.mytomatotrain.create_task.color_picker.ColorItem
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

    val taskToDo = list.size
    val taskDone = list.filter { it.isTaskComplete }.size
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

fun getColorResByName(colorName: String?): Int {
    return when (colorName) {
        "violet_basic" -> R.color.violet_basic
        "pink_doll" -> R.color.pink_doll
        "violet_dark" -> R.color.violet_dark
        "red_basic" -> R.color.red_basic
        "red_dark" -> R.color.red_dark
        "orange_basic" -> R.color.orange_basic
        "ocher" -> R.color.ocher
        "green_apple" -> R.color.green_apple
        "green_grass" -> R.color.green_grass
        "green_ocean" -> R.color.green_ocean
        "blue_basic" -> R.color.blue_basic
        "navy" -> R.color.navy
        "pink_tulip" -> R.color.pink_tulip
        "purple_light" -> R.color.purple_light
        "pink_peach" -> R.color.pink_peach
        "orange_light" -> R.color.orange_light
        "yellow" -> R.color.yellow
        "cyan" -> R.color.cyan
        "green_mermaid" -> R.color.green_mermaid
        "green_soap" -> R.color.green_soap
        "blue_grey" -> R.color.blue_grey
        "green_sage" -> R.color.green_sage
        "grey" -> R.color.grey
        "blue_jeans" -> R.color.blue_jeans
        else -> R.color.violet_basic
    }
}

fun getColorItemList(): List<ColorItem> = listOf(
    ColorItem(false, "violet_basic"),
    ColorItem(false, "pink_doll"),
    ColorItem(false, "violet_dark"),
    ColorItem(false, "red_basic"),
    ColorItem(false, "red_dark"),
    ColorItem(false, "orange_basic"),
    ColorItem(false, "ocher"),
    ColorItem(false, "green_apple"),
    ColorItem(false, "green_grass"),
    ColorItem(false, "green_ocean"),
    ColorItem(false, "blue_basic"),
    ColorItem(false, "navy"),
    ColorItem(false, "pink_tulip"),
    ColorItem(false, "purple_light"),
    ColorItem(false, "pink_peach"),
    ColorItem(false, "orange_light"),
    ColorItem(false, "yellow"),
    ColorItem(false, "cyan"),
    ColorItem(false, "green_mermaid"),
    ColorItem(false, "green_soap"),
    ColorItem(false, "blue_grey"),
    ColorItem(false, "green_sage"),
    ColorItem(false, "grey"),
    ColorItem(false, "blue_jeans")
)