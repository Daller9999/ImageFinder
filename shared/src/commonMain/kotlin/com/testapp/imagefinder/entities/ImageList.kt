package com.testapp.imagefinder.entities

import com.testapp.imagefinder.utils.orNull
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageList(
    @SerialName("total")
    private val _total: Int? = null,
    @SerialName("totalHits")
    private val _totalHits: Int? = null,
    @SerialName("hits")
    private val _hits: List<ImageHit>? = null
) {
    val total get() = _total.orNull()
    val totalHits get() = _totalHits.orNull()
    val hits get() = _hits.orNull()
}