package com.testapp.domain.util

import com.testapp.entities.Image
import com.testapp.imagefinder.entities.ImageHit

fun ImageHit.toImage(): Image = Image(
    id = id,
    pageURL = pageUrl,
    type = type,
    tags = if (tags.isEmpty()) {
        emptyList()
    } else {
        tags.replace(" ", "").split(",")
    },
    previewURL = previewUrl,
    previewWidth = previewWidth,
    previewHeight = previewHeight,
    webFormatURL = webFormatUrl,
    webFormatWidth = webFormatWidth,
    webFormatHeight = webFormatHeight,
    largeImageURL = largeImageUrl,
    imageWidth = imageWidth,
    imageHeight = imageHeight,
    imageSize = imageSize,
    views = views,
    downloads = downloads,
    collections = collections,
    likes = likes,
    comments = comments,
    userId = usedId,
    user = user,
    userImageURL = userImageUrl,
)