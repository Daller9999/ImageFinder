package com.testapp.imagefinder.android.screens.detailedimage.model

import com.testapp.entities.Image

data class DetailedViewState(
    val image: Image = Image(),
    val ratio: Float = 1f
)