package com.testapp.domain.usecase

import com.testapp.domain.interactors.ImageInteractor
import com.testapp.domain.util.toImage
import com.testapp.entities.Image
import com.testapp.imagefinder.core.Configuration
import com.testapp.imagefinder.usecase.ImageApiCall

class ImageUseCase(
    private val imageApiCall: ImageApiCall,
    private val configuration: Configuration
): ImageInteractor {

    override suspend fun findImage(search: String): List<Image> {
        return imageApiCall.findImages(search).hits.map { it.toImage() }
    }

    override fun setApiKey(key: String) {
        configuration.apiKey = key
    }


}