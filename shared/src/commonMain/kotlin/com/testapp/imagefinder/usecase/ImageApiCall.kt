package com.testapp.imagefinder.usecase

import com.testapp.imagefinder.core.ApiLinks
import com.testapp.imagefinder.core.ClientCore
import com.testapp.imagefinder.core.ApiCall
import com.testapp.imagefinder.entities.ImageList
import io.ktor.client.request.*
import io.ktor.http.*

class ImageApiCall(
    private val clientCore: ClientCore
): ApiCall(clientCore) {

    suspend fun findImages(search: String): ImageList {
        val url = "${ApiLinks.BASE_URL}/${ApiLinks.API_ROUTE}/"
        return client.request(url) {
            parameter("q", search)
            parameter("image_type", "photo")
            setBuilderWithKey(HttpMethod.Get)
        }
    }

}