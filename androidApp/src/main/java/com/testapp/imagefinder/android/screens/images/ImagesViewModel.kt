package com.testapp.imagefinder.android.screens.images

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.testapp.domain.interactors.ImageInteractor
import com.testapp.imagefinder.android.core.BaseViewModel
import com.testapp.imagefinder.android.screens.images.model.ImagesEvent
import com.testapp.imagefinder.android.screens.images.model.ImagesViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImagesViewModel(
    private val imageInteractor: ImageInteractor
): BaseViewModel<ImagesViewState, ImagesEvent>(ImagesViewState()) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = imageInteractor.findImage("fruits")
            Log.i("TEST_TEST", result.toString())
        }
    }

    override fun obtainEvent(viewEvent: ImagesEvent) {

    }

}