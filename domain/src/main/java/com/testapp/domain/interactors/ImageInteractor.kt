package com.testapp.domain.interactors

import com.testapp.entities.Image

interface ImageInteractor {

    suspend fun findImage(search: String): List<Image>

    fun setApiKey(key: String)

}