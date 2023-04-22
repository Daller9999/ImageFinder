package com.testapp.imagefinder.android.screens.images

import android.util.Log
import com.testapp.domain.interactors.ImageInteractor
import com.testapp.entities.Image
import com.testapp.imagefinder.android.core.BaseViewModel
import com.testapp.imagefinder.android.screens.images.model.ImagesEvent
import com.testapp.imagefinder.android.screens.images.model.ImagesViewState
import kotlinx.coroutines.Job

class ImagesViewModel(
    private val imageInteractor: ImageInteractor
) : BaseViewModel<ImagesViewState, ImagesEvent>(ImagesViewState()) {

    private var jobLoading: Job? = null
    private var text: String = ""
    private var page: Int = 1

    init {
        uploadImage("fruits")
    }

    override fun obtainEvent(viewEvent: ImagesEvent) {
        when (viewEvent) {
            is ImagesEvent.OnTextChanged -> onTextChanged(viewEvent.text)
            ImagesEvent.OnLoadNext -> onLoadNext()
        }
    }

    private fun onTextChanged(text: String) {
        update { it.copy(textSearch = text) }
        uploadImage(text)
    }

    private fun onLoadNext() {
        uploadImage(text = text)
    }

    private fun uploadImage(text: String) {
        val isSameSearch = text == this.text
        this.text = text
        Log.i("TEST_TEST", "page: $page")
        if (isSameSearch) {
            page++
            jobLoading = launchIO {
                val result = uploadImages()
                update {
                    val list = it.images
                    list.addAll(result)
                    it.copy(images = list)
                }
            }
        } else {
            page = 1
            jobLoading?.cancel()
            jobLoading = launchIO {
                update { it.copy(isLoading = true) }
                val result = uploadImages()
                update {
                    it.copy(
                        images = result,
                        isLoading = false
                    )
                }
            }
        }
    }

    private suspend fun uploadImages(): ArrayList<List<Image>> {
        val result = imageInteractor.findImage(text, page)
        val images = arrayListOf<List<Image>>()
        for (i in result.indices step 2) {
            val arrayList = arrayListOf(result[i])
            if (i + 1 < result.size) {
                arrayList.add(result[i + 1])
            }
            images.add(arrayList)
        }
        return images
    }

}