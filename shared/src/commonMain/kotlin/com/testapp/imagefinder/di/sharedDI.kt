package com.testapp.imagefinder.di

import com.testapp.imagefinder.core.ClientInfo
import com.testapp.imagefinder.core.Configuration
import org.koin.dsl.module

val sharedModule = module {
    single { ClientInfo() }
    single { Configuration() }
}