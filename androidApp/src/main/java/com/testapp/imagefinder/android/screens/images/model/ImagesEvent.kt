package com.testapp.imagefinder.android.screens.images.model

import com.testapp.entities.Image

sealed interface ImagesEvent {
    data class OnTextChanged(val text: String) : ImagesEvent

    data class OnImageClick(val image: Image) : ImagesEvent

    object OnLoadNext : ImagesEvent

    object OnHideKeyboard : ImagesEvent

    object OnCloseDialog : ImagesEvent
}