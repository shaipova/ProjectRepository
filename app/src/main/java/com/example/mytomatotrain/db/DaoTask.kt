package com.example.mytomatotrain.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.mytomatotrain.task.Periodic
import com.example.mytomatotrain.task.Task

@Dao
interface DaoTask {
    // add RxJava later

    @Upsert
    fun upsertTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks_table WHERE id = :id")
    fun getTaskById(id: Int) : Task

    @Query("SELECT * FROM tasks_table WHERE title = :title")
    fun getTaskByName(title: String) : Task

    @Query("SELECT * FROM tasks_table WHERE periodic = :periodic")
    fun getAllTaskFromPeriod(periodic: Periodic) : List<Task>

    @Update
    fun updateTask(task: Task)

    // ... etc
}