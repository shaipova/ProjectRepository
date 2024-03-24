package com.example.timer.di

import com.example.core_api.database.QuotesRepository
import com.example.timer.TimerPresenter
import dagger.Module
import dagger.Provides

@Module
object TimerModule {

    @Provides
    fun provideTimerPresenter(quotesRepository: QuotesRepository): TimerPresenter = TimerPresenter(quotesRepository)
}