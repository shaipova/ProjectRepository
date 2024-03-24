package com.example.core_api.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.core_api.dto.Periodic
import com.example.core_api.dto.Task

@Dao
interface DaoTask {

    @Upsert
    suspend fun upsertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks_table WHERE id = :id")
    suspend fun getTaskById(id: Int) : Task?

    @Query("SELECT * FROM tasks_table WHERE title = :title")
    suspend fun getTaskByName(title: String) : Task?

    @Query("SELECT * FROM tasks_table WHERE periodic = :periodic")
    suspend fun getAllTaskFromPeriod(periodic: Periodic) : List<Task>?

    @Query("SELECT * FROM tasks_table")
    suspend fun getAllTask() : List<Task>?

    @Update
    suspend fun updateTask(task: Task)

}