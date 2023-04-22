package com.testapp.imagefinder.android.screens.images

import com.testapp.domain.interactors.ImageInteractor
import com.testapp.imagefinder.android.core.BaseViewModel
import com.testapp.imagefinder.android.screens.images.model.ImagesEvent
import com.testapp.imagefinder.android.screens.images.model.ImagesViewState

class ImagesViewModel(
    private val imageInteractor: ImageInteractor
) : BaseViewModel<ImagesViewState, ImagesEvent>(ImagesViewState()) {

    init {
        launchIO {
            val result = imageInteractor.findImage("fruits")
            update { it.copy(images = result) }
        }
    }

    override fun obtainEvent(viewEvent: ImagesEvent) {

    }

}