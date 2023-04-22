package com.testapp.imagefinder.android.screens.images

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.testapp.imagefinder.android.screens.images.ImagesViewModel
import com.testapp.imagefinder.android.screens.images.model.ImagesViewState

@Composable
fun ImageScreen(navController: NavController, viewModel: ImagesViewModel) {
    val state = viewModel.viewStates().collectAsState()
    ImageView(
        state = state.value
    )
}

@Composable
private fun ImageView(
    state: ImagesViewState
) {
    Column(modifier = Modifier.fillMaxSize()) {

    }
}