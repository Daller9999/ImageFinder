package com.testapp.imagefinder.android.di

import com.testapp.imagefinder.android.screens.ImagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { ImagesViewModel(get()) }
}