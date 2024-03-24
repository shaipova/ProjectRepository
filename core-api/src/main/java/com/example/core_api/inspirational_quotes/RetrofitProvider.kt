package com.example.core_api.inspirational_quotes

import com.example.core_api.database.QuotesRepository

interface RetrofitProvider {

    fun provideQuotesRepository(): QuotesRepository

}