package com.testapp.imagefinder.android.screens.images

import com.testapp.domain.interactors.ImageInteractor
import com.testapp.entities.Image
import com.testapp.imagefinder.android.core.BaseViewModel
import com.testapp.imagefinder.android.screens.images.model.ImagesEvent
import com.testapp.imagefinder.android.screens.images.model.ImagesViewState

class ImagesViewModel(
    private val imageInteractor: ImageInteractor
) : BaseViewModel<ImagesViewState, ImagesEvent>(ImagesViewState()) {

    init {
        launchIO {
            val result = imageInteractor.findImage("fruits")
            val images = arrayListOf<ArrayList<Image>>()
            for (i in result.indices step 2) {
                val arrayList = arrayListOf(result[i])
                if (i + 1 < result.size) {
                    arrayList.add(result[i + 1])
                }
                images.add(arrayList)
            }
            update { it.copy(images = images) }
        }
    }

    override fun obtainEvent(viewEvent: ImagesEvent) {

    }

}