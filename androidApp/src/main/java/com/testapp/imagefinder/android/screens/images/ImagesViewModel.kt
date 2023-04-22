package com.testapp.imagefinder.android.screens.images

import com.testapp.domain.interactors.ImageInteractor
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
    private var text: String = ""
    private var page: Int = 1

    init {
        uploadImage("fruits")
    }

    override fun obtainEvent(viewEvent: ImagesEvent) {
        when (viewEvent) {
            is ImagesEvent.OnTextChanged -> onTextChanged(viewEvent.text)
            ImagesEvent.OnLoadNext -> onLoadNext()
            ImagesEvent.OnHideKeyboard -> onHideKeyBoard()
        }
    }

    private fun onHideKeyBoard() {
        val text = viewStates().value.textSearch
        if (connectionManager.isOnline()) {
            launchIO {
                val saved = imageInteractor.getSearchStrings()
                update { it.copy(savedSearchWords = saved) }
            }
            uploadImage(text)
        } else {
            launchIO {
                val list = imageInteractor.getSavedSearch(text)
                update { it.copy(images = list.transformList()) }
            }
        }
    }

    private fun onTextChanged(text: String) {
        update { it.copy(textSearch = text) }
    }

    private fun onLoadNext() {
        uploadImage(text = text)
    }

    private fun uploadImage(text: String) {
        val isSameSearch = text == this.text
        this.text = text

        if (text.isEmpty()) return

        if (isSameSearch) {
            page++
            jobLoading = launchIO {
                val result = uploadImages()
                val list = viewStates().value.images
                list.addAll(result)
                imageInteractor.saveSearch(text, list.flatten())
                update { it.copy(images = list) }
            }
        } else {
            page = 1
            jobLoading?.cancel()
            jobLoading = launchIO {
                update { it.copy(isLoading = true) }
                val result = uploadImages()
                imageInteractor.saveSearch(text, result.flatten())
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
        return imageInteractor.findImage(text, page).transformList()
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