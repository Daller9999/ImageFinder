package com.testapp.data.datastore

import com.testapp.entities.ImageList

interface SavedSearchRepository {

    suspend fun insert(savedSearch: ImageList)

    suspend fun getSearchByText(search: String): ImageList

    suspend fun updateSearch(search: String, json: String)

    suspend fun existsSearch(search: String): Boolean

}