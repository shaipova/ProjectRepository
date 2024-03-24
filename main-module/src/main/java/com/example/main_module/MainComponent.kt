package com.example.main_module

import com.example.core_api.ProvidersFacade
import dagger.Component


@Component(
    dependencies = [ProvidersFacade::class],
    modules = [MainModule::class]
)
interface MainComponent {

    companion object {

        fun create(providersFacade: ProvidersFacade): MainComponent {
            return DaggerMainComponent.builder().providersFacade(providersFacade).build()
        }
    }

    fun inject(mainActivity: MainActivity)
}