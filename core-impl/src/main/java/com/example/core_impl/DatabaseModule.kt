package com.example.core_impl

import android.content.Context
import androidx.room.Room
import com.example.core_api.database.DaoTask
import com.example.core_api.database.TasksDatabaseContract
import com.example.core_api.database.TasksRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Reusable
    fun provideDao(tasksDatabaseContract: TasksDatabaseContract): DaoTask {
        return tasksDatabaseContract.daoTask()
    }

    @Provides
    @Singleton
    fun provideTasksDatabase(context: Context): TasksDatabaseContract {
        return Room.databaseBuilder(
            context,
            TasksDatabase::class.java,
            "tasks_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(tasksDataBase: TasksDatabaseContract) : TasksRepository {
        return TasksRepositoryImpl(tasksDataBase)
    }
}