package com.testapp.domain.usecase

import com.testapp.domain.interactors.ImageInteractor
import com.testapp.domain.util.makeApiCall
import com.testapp.domain.util.toImage
import com.testapp.entities.Image
import com.testapp.imagefinder.core.Configuration
import com.testapp.imagefinder.usecase.ImageApiCall

internal class ImageUseCase(
    private val imageApiCall: ImageApiCall,
    private val configuration: Configuration
): ImageInteractor {

    override suspend fun findImage(
        search: String
    ): List<Image> = makeApiCall {
        imageApiCall.findImages(search).hits.map { it.toImage() }
    } ?: emptyList()

    override fun setApiKey(key: String) {
        configuration.apiKey = key
    }


}