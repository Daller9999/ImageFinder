package com.testapp.imagefinder.di

import com.testapp.imagefinder.core.ClientCore
import com.testapp.imagefinder.core.ClientInfo
import com.testapp.imagefinder.core.Configuration
import com.testapp.imagefinder.usecase.ImageApiCall
import org.koin.dsl.module
import kotlin.math.sin

internal val coreModule = module {
    single { ClientInfo() }
    single { Configuration() }
    single { ClientCore(get(), get()) }
}

internal val apiModule = module {
    factory { ImageApiCall(get()) }
}

val sharedModule = coreModule + apiModule