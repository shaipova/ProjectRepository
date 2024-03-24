package com.example.time_period_schedule.di

import com.example.core_api.ProvidersFacade
import com.example.time_period_schedule.TimePeriodScheduleFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
    (dependencies = [ProvidersFacade::class],
    modules = [TimePeriodScheduleModule::class])
interface TimePeriodScheduleComponent {

    companion object {

        fun create(providersFacade: ProvidersFacade): TimePeriodScheduleComponent {
            return DaggerTimePeriodScheduleComponent
                .builder()
                .providersFacade(providersFacade)
                .build()
        }
    }


    fun inject(fragment: TimePeriodScheduleFragment)
}