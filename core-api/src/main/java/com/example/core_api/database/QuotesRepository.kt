package com.example.core_api.database

import com.example.core_api.dto.InspirationalQuote

interface QuotesRepository {
    suspend fun getQuote(): List<InspirationalQuote>
}