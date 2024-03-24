package com.example.schedule.di

import com.example.core_api.ProvidersFacade
import com.example.schedule.ScheduleFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
    (dependencies = [ProvidersFacade::class],
    modules = [ScheduleModule::class])
interface ScheduleComponent {

    companion object {

        fun create(providersFacade: ProvidersFacade): ScheduleComponent {
            return DaggerScheduleComponent
                .builder()
                .providersFacade(providersFacade)
                .build()
        }
    }
    fun inject(fragment: ScheduleFragment)
}