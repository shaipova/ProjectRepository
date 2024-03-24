package com.example.timer.di

import com.example.core_api.ProvidersFacade
import com.example.timer.TimerFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
    (dependencies = [ProvidersFacade::class],
    modules = [TimerModule::class])
interface TimerComponent {

    companion object {

        fun create(providersFacade: ProvidersFacade): TimerComponent {
            return DaggerTimerComponent
                .builder()
                .providersFacade(providersFacade)
                .build()
        }
    }

    fun inject(fragment: TimerFragment)
}