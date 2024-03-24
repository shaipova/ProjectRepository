package com.example.schedule.di

import com.example.core_api.database.TasksRepository
import com.example.create_task_api.CreateTaskMediator
import com.example.schedule.SchedulePresenter
import com.example.time_period_schedule_api.TimePeriodScheduleMediator
import com.example.timer_api.TimerMediator
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
object ScheduleModule {

    @Provides
    fun provideSchedulePresenter(repository: TasksRepository) : SchedulePresenter = SchedulePresenter(repository)

    @Provides
    fun provideCreateTaskMediator(map: Map<Class<*>, @JvmSuppressWildcards Provider<Any>>)
            : CreateTaskMediator {
        return map[CreateTaskMediator::class.java]!!.get() as CreateTaskMediator
    }

    @Provides
    fun provideTimePeriodScheduleMediator(map: Map<Class<*>, @JvmSuppressWildcards Provider<Any>>)
            : TimePeriodScheduleMediator {
        return map[TimePeriodScheduleMediator::class.java]!!.get() as TimePeriodScheduleMediator
    }

    @Provides
    fun provideTimerMediator(map: Map<Class<*>, @JvmSuppressWildcards Provider<Any>>)
            : TimerMediator {
        return map[TimerMediator::class.java]!!.get() as TimerMediator
    }
}