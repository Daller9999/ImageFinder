package com.testapp.imagefinder.android

import android.app.Application
import com.testapp.domain.di.domainModules
import com.testapp.imagefinder.android.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(viewModels + domainModules)
        }
    }

}