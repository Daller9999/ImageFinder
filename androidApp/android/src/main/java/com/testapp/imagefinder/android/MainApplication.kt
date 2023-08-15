package com.testapp.imagefinder.android

import android.app.Application
import com.testapp.domain.di.domainModules
import com.testapp.domain.interactors.ImageInteractor
import com.testapp.imagefinder.android.di.appModules
import com.testapp.imagefinder.android.di.viewModels
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(appModules + domainModules)
        }
    }

}