package com.example.time_period_schedule.di

import com.example.core_api.database.TasksRepository
import com.example.create_task_api.CreateTaskMediator
import com.example.time_period_schedule.TimePeriodSchedulePresenter
import com.example.timer_api.TimerMediator
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
object TimePeriodScheduleModule {

    @Provides
    fun provideTimePeriodSchedulePresenter(repository: TasksRepository): TimePeriodSchedulePresenter = TimePeriodSchedulePresenter(repository)

    @Provides
    fun provideCreateTaskMediator(map: Map<Class<*>, @JvmSuppressWildcards Provider<Any>>)
            : CreateTaskMediator {
        return map[CreateTaskMediator::class.java]!!.get() as CreateTaskMediator
    }

    @Provides
    fun provideTimerMediator(map: Map<Class<*>, @JvmSuppressWildcards Provider<Any>>)
            : TimerMediator {
        return map[TimerMediator::class.java]!!.get() as TimerMediator
    }
}