package com.testapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.testapp.data.room.entities.SavedSearch

@Dao
internal interface SavedSearchDao {

    @Insert
    fun insert(savedSearch: SavedSearch)

    @Query("select * from SavedSearch where searchWord = :search")
    fun getSearchByText(search: String): SavedSearch

    @Query("update SavedSearch set searchJson = :json where searchWord = :search")
    fun updateSearch(search: String, json: String)

    @Query("select exists (select 1 from SavedSearch where searchWord = :search)")
    fun existsSearch(search: String): Boolean

    @Query("select searchWord from SavedSearch")
    fun getSearchStrings(): List<String>

}