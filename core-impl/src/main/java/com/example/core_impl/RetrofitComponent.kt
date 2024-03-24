package com.example.core_impl

import com.example.core_api.inspirational_quotes.RetrofitProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [QuotesModule::class]
)
interface RetrofitComponent : RetrofitProvider