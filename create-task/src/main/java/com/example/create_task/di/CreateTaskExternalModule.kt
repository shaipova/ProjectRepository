package com.example.create_task.di

import com.example.create_task.CreateTaskMediatorImpl
import com.example.create_task_api.CreateTaskMediator
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface CreateTaskExternalModule {

    @Binds
    @IntoMap
    @ClassKey(CreateTaskMediator::class)
    fun bindMediator(mediator: CreateTaskMediatorImpl): Any
}