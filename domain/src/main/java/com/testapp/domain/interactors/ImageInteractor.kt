package com.testapp.domain.interactors

import com.testapp.entities.Image

interface ImageInteractor {

    suspend fun findImage(search: String, page: Int): List<Image>

    fun setApiKey(key: String)

    suspend fun saveSearch(search: String, list: List<Image>)

    suspend fun getSavedSearch(search: String): List<Image>

    suspend fun getSearchStrings(): List<String>

}