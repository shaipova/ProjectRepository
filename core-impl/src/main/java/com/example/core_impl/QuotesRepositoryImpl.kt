package com.example.core_impl

import com.example.core_api.database.QuotesRepository
import com.example.core_api.dto.InspirationalQuote
import com.example.core_api.inspirational_quotes.QuotesService
import javax.inject.Inject

class QuotesRepositoryImpl @Inject constructor(
    private val service: QuotesService
) : QuotesRepository {
    override suspend fun getQuote(): List<InspirationalQuote> = service.getQuotes()
}