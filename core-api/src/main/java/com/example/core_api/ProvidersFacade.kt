package com.example.core_api

import com.example.core_api.database.DatabaseProvider
import com.example.core_api.inspirational_quotes.RetrofitProvider

interface ProvidersFacade : MediatorsProvider, DatabaseProvider, AppProvider, RetrofitProvider