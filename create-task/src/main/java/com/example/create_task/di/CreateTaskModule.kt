package com.example.create_task.di

import com.example.core_api.database.TasksRepository
import com.example.create_task.CreateTaskPresenter
import com.example.schedule_api.ScheduleMediator
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
object CreateTaskModule {

    @Provides
    fun provideCreateTaskPresenter(repository: TasksRepository) : CreateTaskPresenter = CreateTaskPresenter(repository)

    @Provides
    fun provideScheduleMediator(map: Map<Class<*>, @JvmSuppressWildcards Provider<Any>>)
            : ScheduleMediator {
        return map[ScheduleMediator::class.java]!!.get() as ScheduleMediator
    }
}