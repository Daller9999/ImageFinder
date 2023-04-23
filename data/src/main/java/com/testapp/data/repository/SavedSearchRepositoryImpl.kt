package com.testapp.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.testapp.data.datastore.SavedSearchRepository
import com.testapp.data.room.dao.SavedSearchDao
import com.testapp.data.room.entities.SavedSearch
import com.testapp.entities.Image
import com.testapp.entities.ImageList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class SavedSearchRepositoryImpl(
    private val searchDao: SavedSearchDao
) : SavedSearchRepository {

    private val gson = Gson()
    private val typedValue = object : TypeToken<List<Image>>() {}.type

    override suspend fun insert(
        savedSearch: ImageList
    ) = withContext(Dispatchers.IO) {
        searchDao.insert(savedSearch.toSavedSearch())
    }

    override suspend fun getSearchByText(
        search: String
    ): ImageList = withContext(Dispatchers.IO) {
        val info = searchDao.getSearchByText(search)
        ImageList(
            search = search,
            images = gson.fromJson(info.searchJson, typedValue)
        )
    }

    override suspend fun updateSearch(
        search: String,
        list: List<Image>
    ) = withContext(Dispatchers.IO) {
        searchDao.updateSearch(search, gson.toJson(list))
    }

    override suspend fun existsSearch(
        search: String
    ): Boolean = withContext(Dispatchers.IO) {
        searchDao.existsSearch(search)
    }

    override suspend fun getSearchStrings(): List<String> = withContext(Dispatchers.IO) {
        searchDao.getSearchStrings()
    }

    private fun ImageList.toSavedSearch() = SavedSearch(
        searchWord = search,
        searchJson = gson.toJson(images)
    )


}