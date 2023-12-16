package com.example.mytomatotrain

import androidx.room.Room
import com.example.mytomatotrain.create_task.CreateTaskPresenter
import com.example.mytomatotrain.db.Repository
import com.example.mytomatotrain.db.RepositoryImpl
import com.example.mytomatotrain.db.TasksDatabase
import com.example.mytomatotrain.scheduler.SchedulePresenter
import com.example.mytomatotrain.task.Periodic
import com.example.mytomatotrain.time_period.PeriodicPresenter
import com.example.mytomatotrain.timer.TimerPresenter
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext().applicationContext,
            TasksDatabase::class.java,
            "tasks_db"
        ).build()
    }

    single<Repository> {
        RepositoryImpl(get())
    }

    factory {
        CreateTaskPresenter(get())
    }

    factory {
        PeriodicPresenter(get())
    }
    factory {
        TimerPresenter(get())
    }
    factory {
        SchedulePresenter(get())
    }
}