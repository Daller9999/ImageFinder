package com.testapp.domain.di

import com.testapp.domain.interactors.ImageInteractor
import com.testapp.domain.usecase.ImageUseCase
import com.testapp.imagefinder.di.sharedModule
import org.koin.dsl.module


val domainInteractors = module {
    factory<ImageInteractor> { ImageUseCase(get(), get()) }
}

val domainModules = sharedModule + domainInteractors