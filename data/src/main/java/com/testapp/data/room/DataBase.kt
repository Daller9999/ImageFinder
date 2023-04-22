package com.testapp.data.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.testapp.data.room.dao.SavedSearchDao
import com.testapp.data.room.entities.SavedSearch

@Database(entities = [SavedSearch::class], version = 1)
internal abstract class DataBase : RoomDatabase() {
    companion object {
        fun buildDataBase(application: Application): DataBase {
            return Room.databaseBuilder(
                application.applicationContext,
                DataBase::class.java,
                "DataBaseSearch"
            ).build()
        }
    }

    abstract fun getSavedSearchDao(): SavedSearchDao
}