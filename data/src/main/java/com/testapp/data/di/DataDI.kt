package com.testapp.data.di

import com.testapp.data.datastore.SavedSearchRepository
import com.testapp.data.repository.SavedSearchRepositoryImpl
import com.testapp.data.room.DataBase
import org.koin.dsl.module

val dataBaseModule = module {
    single { DataBase.buildDataBase(get()) }

    fun provideSavedSearchDao(dbMain: DataBase) = dbMain.getSavedSearchDao()

    single { provideSavedSearchDao(get()) }

    factory<SavedSearchRepository> { SavedSearchRepositoryImpl(get()) }
}