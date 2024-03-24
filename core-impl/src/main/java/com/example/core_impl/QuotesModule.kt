package com.example.core_impl

import com.example.core_api.database.QuotesRepository
import com.example.core_api.inspirational_quotes.QuotesService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class QuotesModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://type.fit/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideQuotesService(retrofit: Retrofit): QuotesService {
        return retrofit.create(QuotesService::class.java)
    }

    @Provides
    @Singleton
    fun provideQuotesRepository(service: QuotesService) : QuotesRepository {
        return QuotesRepositoryImpl(service)
    }
}