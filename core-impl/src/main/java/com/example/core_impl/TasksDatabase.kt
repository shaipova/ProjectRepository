package com.example.core_impl

import androidx.room.Database
import androidx.room.TypeConverters
import androidx.room.RoomDatabase
import com.example.core_api.database.TasksDatabaseContract
import com.example.core_api.dto.ListTomatoesConverter
import com.example.core_api.dto.Task

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListTomatoesConverter::class)
abstract class TasksDatabase: RoomDatabase(), TasksDatabaseContract