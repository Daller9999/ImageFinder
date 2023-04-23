package com.testapp.imagefinder.android.screens.detailedimage.model

import com.testapp.entities.Image

sealed interface DetailedEvent {
    data class PutArgs(val image: Image) : DetailedEvent
}