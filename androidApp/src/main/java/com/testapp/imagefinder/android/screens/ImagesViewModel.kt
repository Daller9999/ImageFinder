package com.testapp.imagefinder.android.screens

import androidx.lifecycle.viewModelScope
import com.testapp.domain.interactors.ImageInteractor
import com.testapp.imagefinder.android.core.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImagesViewModel(
    private val imageInteractor: ImageInteractor
): BaseViewModel<ImagesViewState, ImagesEvent>(ImagesViewState()) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = imageInteractor.findImage("fruits")
        }
    }

    override fun obtainEvent(viewEvent: ImagesEvent) {

    }

}