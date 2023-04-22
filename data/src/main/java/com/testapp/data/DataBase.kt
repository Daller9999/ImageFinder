package com.testapp.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.testapp.data.dao.SavedSearchDao
import com.testapp.data.dao.SearchHistoryDao
import com.testapp.data.entities.SavedSearch
import com.testapp.data.entities.SearchHistory

@Database(entities = [SavedSearch::class, SearchHistory::class], version = 1)
abstract class DataBase : RoomDatabase() {
    companion object {
        fun buildDataBase(application: Application): DataBase {
            return Room.databaseBuilder(
                application.applicationContext,
                DataBase::class.java,
                "DataBaseExchanges"
            ).build()
        }
    }

    abstract fun getSearchHistoryDao(): SearchHistoryDao

    abstract fun getSavedSearchDao(): SavedSearchDao
}