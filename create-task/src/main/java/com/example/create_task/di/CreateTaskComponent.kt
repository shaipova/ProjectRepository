package com.example.create_task.di

import com.example.core_api.ProvidersFacade
import com.example.create_task.CreateTaskFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
    (dependencies = [ProvidersFacade::class],
    modules = [CreateTaskModule::class])
interface CreateTaskComponent {

    companion object {

        fun create(providersFacade: ProvidersFacade): CreateTaskComponent {
            return DaggerCreateTaskComponent
                .builder()
                .providersFacade(providersFacade)
                .build()
        }
    }

    fun inject(fragment: CreateTaskFragment)
}