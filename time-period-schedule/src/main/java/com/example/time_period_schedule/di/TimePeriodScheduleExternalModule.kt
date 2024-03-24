package com.example.time_period_schedule.di

import com.example.time_period_schedule.TimePeriodScheduleMediatorImpl
import com.example.time_period_schedule_api.TimePeriodScheduleMediator
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface TimePeriodScheduleExternalModule {

    @Binds
    @IntoMap
    @ClassKey(TimePeriodScheduleMediator::class)
    fun bindMediator(mediator: TimePeriodScheduleMediatorImpl): Any
}