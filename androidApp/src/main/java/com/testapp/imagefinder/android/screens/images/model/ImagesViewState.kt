package com.testapp.imagefinder.android.screens.images.model

import com.testapp.entities.Image

data class ImagesViewState(
    val images: List<Image> = emptyList()
)