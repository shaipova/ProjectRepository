package com.example.core_api.inspirational_quotes

import com.example.core_api.dto.InspirationalQuote
import retrofit2.http.GET

interface QuotesService {
    @GET("quotes")
    suspend fun getQuotes(): List<InspirationalQuote>
}