package com.example.mytomatotrain

import android.app.Application
import com.example.core.CoreProvidersFactory
import com.example.core_api.AppProvider
import com.example.core_api.ProvidersFacade
import com.example.core_api.database.DatabaseProvider
import com.example.core_api.inspirational_quotes.RetrofitProvider
import com.example.create_task.di.CreateTaskExternalModule
import com.example.schedule.di.ScheduleExternalModule
import com.example.time_period_schedule.di.TimePeriodScheduleExternalModule
import com.example.timer.di.TimerExternalModule
import dagger.Component

@Component(
    dependencies = [AppProvider::class, DatabaseProvider::class, RetrofitProvider::class],
    modules = [
        TimerExternalModule::class,
        ScheduleExternalModule::class,
        TimePeriodScheduleExternalModule::class,
        CreateTaskExternalModule::class
    ]
)
interface FacadeComponent : ProvidersFacade {

    companion object {
        fun init(application: Application): FacadeComponent =
            DaggerFacadeComponent.builder()
                .appProvider(AppComponent.create(application))
                .databaseProvider(CoreProvidersFactory.createDatabaseBuilder(AppComponent.create(application)))
                .retrofitProvider(CoreProvidersFactory.createRetrofitBuilder())
                .build()
    }

}