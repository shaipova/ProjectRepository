package com.example.mytomatotrain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mytomatotrain.db.DaoTask
import com.example.mytomatotrain.task.ListTomatoesConverter
import com.example.mytomatotrain.task.Task

@Database(
    entities = [Task::class],
    version = 1
)
@TypeConverters(ListTomatoesConverter::class)
abstract class TasksDatabase: RoomDatabase() {
    abstract val dao: DaoTask
}