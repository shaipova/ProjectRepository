package com.example.core

import com.example.core_api.AppProvider
import com.example.core_api.database.DatabaseProvider
import com.example.core_api.inspirational_quotes.RetrofitProvider
import com.example.core_impl.DaggerDatabaseComponent
import com.example.core_impl.DaggerRetrofitComponent

object CoreProvidersFactory {
    fun createDatabaseBuilder(appProvider: AppProvider): DatabaseProvider {
        return DaggerDatabaseComponent.builder().appProvider(appProvider).build()
    }

    fun createRetrofitBuilder(): RetrofitProvider {
        return DaggerRetrofitComponent.builder().build()
    }
}