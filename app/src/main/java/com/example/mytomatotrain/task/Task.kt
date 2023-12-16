package com.example.mytomatotrain.task

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

@Entity(tableName = "tasks_table")
data class Task(
    @PrimaryKey(true)
    val id: Int = 0,
    var listTomatoes: MutableList<Tomato> = mutableListOf(),
    val title: String,
    val color: Int,
    val periodic: Periodic
): Serializable {
    val isTaskComplete: Boolean
        get() = listTomatoes.all { it.status == Status.DONE }
}

enum class Periodic(val title: String) {
    EVERYDAY_TASK("Everyday"), WEEKLY_TASK("Weekly")
}

class ListTomatoesConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromListTomatoes(listTomatoes: MutableList<Tomato>): String {
        return gson.toJson(listTomatoes)
    }

    @TypeConverter
    fun toListTomatoes(data: String): MutableList<Tomato> {
        val listType = object : TypeToken<MutableList<Tomato>>() {}.type
        return gson.fromJson(data, listType)
    }
}