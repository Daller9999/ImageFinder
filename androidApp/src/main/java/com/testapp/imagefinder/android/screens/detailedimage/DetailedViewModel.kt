package com.testapp.imagefinder.android.screens.detailedimage

import com.testapp.entities.Image
import com.testapp.imagefinder.android.core.BaseViewModel
import com.testapp.imagefinder.android.screens.detailedimage.model.DetailedEvent
import com.testapp.imagefinder.android.screens.detailedimage.model.DetailedViewState

class DetailedViewModel : BaseViewModel<DetailedViewState, DetailedEvent>(DetailedViewState()) {
    override fun obtainEvent(viewEvent: DetailedEvent) {
        when (viewEvent) {
            is DetailedEvent.PutArgs -> onUploadImage(viewEvent.image)
        }
    }

    private fun onUploadImage(image: Image) {
        update {
            it.copy(
                image = image,
                ratio = image.imageWidth.toFloat() / image.imageHeight.toFloat()
            )
        }
    }
}