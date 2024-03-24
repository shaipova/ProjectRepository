package com.example.timer.di

import com.example.timer.TimerMediatorImpl
import com.example.timer_api.TimerMediator
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface TimerExternalModule {

    @Binds
    @IntoMap
    @ClassKey(TimerMediator::class)
    fun bindMediator(mediator: TimerMediatorImpl): Any
}