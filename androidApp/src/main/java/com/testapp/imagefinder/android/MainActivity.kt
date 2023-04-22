package com.testapp.imagefinder.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.testapp.domain.interactors.ImageInteractor
import com.testapp.imagefinder.android.screens.images.ImageScreen
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContent { App() }
    }

    private fun init() {
        val imageInteractor by inject<ImageInteractor>()
        imageInteractor.setApiKey(BuildConfig.API_KEY)
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.HOME.name
    ) {
        composable(Screens.HOME.name) {
            ImageScreen(
                navController = navController,
                viewModel = koinViewModel()
            )
        }
    }
}

enum class Screens(name: String) {
    HOME("HOME"),
    DETAILED("DETAILED")
}