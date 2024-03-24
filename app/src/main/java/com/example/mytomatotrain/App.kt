package com.example.mytomatotrain

import android.app.Application
import com.example.core_api.AppWithFacade
import com.example.core_api.ProvidersFacade

class App : Application(), AppWithFacade {

    companion object {

        private var facadeComponent: FacadeComponent? = null
    }

    override fun getFacade(): ProvidersFacade {
        return facadeComponent ?: FacadeComponent.init(this).also {
            facadeComponent = it
        }
    }

    override fun onCreate() {
        super.onCreate()
        getFacade()
    }
}