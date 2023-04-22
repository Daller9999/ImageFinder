package com.testapp.imagefinder.entities

import com.testapp.imagefinder.utils.orNull
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageHit(
    @SerialName("id")
    private val _id: Int? = null,
    @SerialName("pageUrl")
    private val _pageURL: String? = null,
    @SerialName("type")
    private val _type: String? = null,
    @SerialName("tags")
    private val _tags: String? = null,//"spring bird, bird, tit",
    @SerialName("previewURL")
    private val _previewURL: String? = null,
    @SerialName("previewWidth")
    private val _previewWidth: Int? = null,
    @SerialName("previewHeight")
    private val _previewHeight: Int? = null,
    @SerialName("webformatURL")
    private val _webformatURL: String? = null,
    @SerialName("webformatWidth")
    private val _webformatWidth: Int? = null,
    @SerialName("webformatHeight")
    private val _webformatHeight: Int? = null,
    @SerialName("largeImageURL")
    private val _largeImageURL: String? = null,
    @SerialName("imageWidth")
    private val _imageWidth: Int? = null,
    @SerialName("imageHeight")
    private val _imageHeight: Int? = null,
    @SerialName("imageSize")
    private val _imageSize: Int? = null,
    @SerialName("views")
    private val _views: Int? = null,
    @SerialName("downloads")
    private val _downloads: Int? = null,
    @SerialName("collections")
    private val _collections: Int? = null,
    @SerialName("likes")
    private val _likes: Int? = null,
    @SerialName("comments")
    private val _comments: Int? = null,
    @SerialName("user_id")
    private val _user_id: Int? = null,
    @SerialName("user")
    private val _user: String? = null,
    @SerialName("userImageURL")
    private val _userImageURL: String? = null,
) {
    val id get() = _id.orNull()
    val pageUrl get() = _pageURL.orNull()
    val type get() = _type.orNull()
    val tags get() = _tags.orNull()
    val previewUrl get() = _previewURL.orNull()
    val previewWidth get() = _previewWidth.orNull()
    val previewHeight get() = _previewHeight.orNull()
    val webFormatUrl get() = _webformatURL.orNull()
    val webFormatWidth get() = _webformatWidth.orNull()
    val webFormatHeight get() = _webformatHeight.orNull()
    val largeImageUrl get() = _largeImageURL.orNull()
    val imageWidth get() = _imageWidth.orNull()
    val imageHeight get() = _imageHeight.orNull()
    val imageSize get() = _imageSize.orNull()
    val views get() = _views.orNull()
    val downloads get() = _downloads.orNull()
    val collections get() = _collections.orNull()
    val likes get() = _likes.orNull()
    val comments get() = _comments.orNull()
    val usedId get() = _user_id.orNull()
    val user get() = _user.orNull()
    val userImageUrl get() = _userImageURL.orNull()
}