package com.example.main_module

import com.example.schedule_api.ScheduleMediator
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
object MainModule {
    @Provides
    fun provideScheduleMediator(map: Map<Class<*>, @JvmSuppressWildcards Provider<Any>>)
            : ScheduleMediator {
        return map[ScheduleMediator::class.java]!!.get() as ScheduleMediator
    }
}