package com.testapp.imagefinder.android.screens.detailedimage

import com.testapp.imagefinder.android.core.BaseViewModel
import com.testapp.imagefinder.android.screens.detailedimage.model.DetailedEvent
import com.testapp.imagefinder.android.screens.detailedimage.model.DetailedViewState

class DetailedViewModel : BaseViewModel<DetailedViewState, DetailedEvent>(DetailedViewState()) {
    override fun obtainEvent(viewEvent: DetailedEvent) {

    }
}