package com.example.schedule.di

import com.example.schedule.ScheduleMediatorImpl
import com.example.schedule_api.ScheduleMediator
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface ScheduleExternalModule {

    @Binds
    @IntoMap
    @ClassKey(ScheduleMediator::class)
    fun bindMediator(mediator: ScheduleMediatorImpl): Any
}