package com.testapp.domain.usecase

import com.testapp.data.datastore.SavedSearchRepository
import com.testapp.domain.interactors.ImageInteractor
import com.testapp.domain.util.makeApiCall
import com.testapp.domain.util.toImage
import com.testapp.entities.Image
import com.testapp.entities.ImageList
import com.testapp.imagefinder.core.Configuration
import com.testapp.imagefinder.usecase.ImageApiCall

internal class ImageUseCase(
    private val imageApiCall: ImageApiCall,
    private val configuration: Configuration,
    private val savedSearchRepository: SavedSearchRepository
) : ImageInteractor {

    override suspend fun findImage(
        search: String,
        page: Int
    ): List<Image> = makeApiCall {
        imageApiCall.findImages(
            search = search,
            page = page
        ).hits.map { it.toImage() }
    } ?: emptyList()

    override fun setApiKey(key: String) {
        configuration.apiKey = key
    }

    override suspend fun saveSearch(search: String, list: List<Image>) {
        if (savedSearchRepository.existsSearch(search)) {
            savedSearchRepository.updateSearch(search, list)
        } else {
            savedSearchRepository.insert(ImageList(search, list))
        }
    }

    override suspend fun getSavedSearch(search: String): List<Image> {
        return if (savedSearchRepository.existsSearch(search)) {
            savedSearchRepository.getSearchByText(search).images
        } else {
            emptyList()
        }
    }

    override suspend fun getSearchStrings(): List<String> = savedSearchRepository.getSearchStrings()


}