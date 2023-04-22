package com.testapp.imagefinder.android.screens.images.model

import com.testapp.entities.Image

data class ImagesViewState(
    val images: ArrayList<List<Image>> = arrayListOf(),
    val isLoading: Boolean = false,
    val textSearch: String = "fruits"
)