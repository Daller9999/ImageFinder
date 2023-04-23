package com.testapp.imagefinder.android.screens.images

import com.testapp.domain.interactors.ImageInteractor
import com.testapp.domain.usecase.Errors
import com.testapp.entities.Image
import com.testapp.imagefinder.android.core.BaseViewModel
import com.testapp.imagefinder.android.network.ConnectionManager
import com.testapp.imagefinder.android.screens.images.model.ImagesEvent
import com.testapp.imagefinder.android.screens.images.model.ImagesViewState
import kotlinx.coroutines.Job

class ImagesViewModel(
    private val imageInteractor: ImageInteractor,
    private val connectionManager: ConnectionManager
) : BaseViewModel<ImagesViewState, ImagesEvent>(ImagesViewState()) {

    private var jobLoading: Job? = null
    private var page: Int = 1
    private var isEndOfSearch: Boolean = false

    init {
        onUploadRequest()
    }

    override fun obtainEvent(viewEvent: ImagesEvent) {
        when (viewEvent) {
            is ImagesEvent.OnTextChanged -> onTextChanged(viewEvent.text)
            ImagesEvent.OnLoadNext -> onLoadNext()
            ImagesEvent.OnHideKeyboard -> onUploadRequest()
            ImagesEvent.OnCloseDialog -> onCloseDialog()
            is ImagesEvent.OnImageClick -> onImageClick(viewEvent.image)
        }
    }

    private fun onCloseDialog() {
        update { it.copy(isVisibleDialog = false) }
    }

    private fun onImageClick(image: Image) {
        update {
            it.copy(
                selectedImage = image,
                isVisibleDialog = true
            )
        }
    }

    private fun onLoadNext() {
        if (isEndOfSearch) return

        page++
        jobLoading = launchIO {
            val result = uploadImages()
            if (result.isEmpty()) return@launchIO

            val state = viewStates().value
            val list = state.images
            list.addAll(result)
            imageInteractor.saveSearch(state.textSearch, list.flatten())
            updateSearchHistory()
            update { it.copy(images = list) }
        }
    }

    private fun onUploadRequest() {
        val text = viewStates().value.textSearch
        if (connectionManager.isOnline()) {
            uploadImage()
        } else {
            launchIO {
                val list = imageInteractor.getSavedSearch(text)
                update { it.copy(images = list.transformList()) }
            }
        }
    }

    private suspend fun updateSearchHistory() {
        val saved = imageInteractor.getSearchStrings()
        update { it.copy(savedSearchWords = saved) }
    }

    private fun onTextChanged(text: String) {
        update { it.copy(textSearch = text) }
    }

    private fun uploadImage() {
        val search = viewStates().value.textSearch
        page = 1
        isEndOfSearch = false
        jobLoading?.cancel()
        jobLoading = launchIO {
            update { it.copy(isLoading = true) }
            val result = uploadImages()
            if (result.isNotEmpty()) {
                imageInteractor.saveSearch(search, result.flatten())
                updateSearchHistory()
            }
            update {
                it.copy(
                    images = result,
                    isLoading = false
                )
            }
        }
    }

    private suspend fun uploadImages(): ArrayList<List<Image>> {
        val result = imageInteractor.findImage(viewStates().value.textSearch, page)
        return if (result.second == Errors.END_OF_SEARCH) {
            isEndOfSearch = true
            arrayListOf()
        } else {
            result.first.transformList()
        }
    }

    private fun List<Image>.transformList(): ArrayList<List<Image>> {
        val images = arrayListOf<List<Image>>()
        for (i in this.indices step 2) {
            val arrayList = arrayListOf(this[i])
            if (i + 1 < size) {
                arrayList.add(this[i + 1])
            }
            images.add(arrayList)
        }
        return images
    }

}