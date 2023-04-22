package com.testapp.imagefinder.android.screens.images.model

sealed interface ImagesEvent {
    data class OnTextChanged(val text: String) : ImagesEvent
}