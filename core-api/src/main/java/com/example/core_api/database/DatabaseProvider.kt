package com.example.core_api.database

interface DatabaseProvider {

    fun daoTask(): DaoTask
    fun provideDatabase(): TasksDatabaseContract
    fun provideRepository(): TasksRepository
}

interface TasksDatabaseContract {

    fun daoTask(): DaoTask
}