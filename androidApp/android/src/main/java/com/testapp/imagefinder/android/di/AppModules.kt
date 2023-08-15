package com.testapp.imagefinder.android.di

import com.testapp.imagefinder.android.network.ConnectionManager
import com.testapp.imagefinder.android.screens.detailedimage.DetailedViewModel
import com.testapp.imagefinder.android.screens.images.ImagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { ImagesViewModel(get(), get()) }
    viewModel { DetailedViewModel() }
}

val connectionModule = module {
    single { ConnectionManager(get()) }
}

val appModules = viewModels + connectionModule