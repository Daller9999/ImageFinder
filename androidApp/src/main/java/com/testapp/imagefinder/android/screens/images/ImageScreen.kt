package com.testapp.imagefinder.android.screens.images

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.testapp.coreui.TagView
import com.testapp.entities.Image
import com.testapp.imagefinder.android.screens.images.model.ImagesEvent
import com.testapp.imagefinder.android.screens.images.model.ImagesViewState

@Composable
fun ImageScreen(navController: NavController, viewModel: ImagesViewModel) {
    val state = viewModel.viewStates().collectAsState()
    ImageView(
        state = state.value,
        onTextChange = { viewModel.obtainEvent(ImagesEvent.OnTextChanged(it)) }
    )
}

@Composable
private fun ImageView(
    state: ImagesViewState,
    onTextChange: (String) -> Unit
) {
    val text = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .background(color = Color.White)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text.value,
            onValueChange = {
                text.value = it
                onTextChange.invoke(it)
            }
        )
        LazyColumn {
            items(state.images) {
                ImageRow(it)
            }
        }
    }
}

@Composable
private fun ImageRow(images: List<Image>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (element in images) {
            ImageViewInfo(
                modifier = Modifier.weight(1f),
                image = element
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ImageViewInfo(modifier: Modifier, image: Image) {
    Column(modifier = modifier.padding(all = 2.dp)) {
        Card(shape = RoundedCornerShape(size = 20.dp)) {
            AsyncImage(
                modifier = Modifier.height(220.dp),
                model = image.webFormatURL,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        Text(
            text = image.user,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(5.dp))
        FlowRow {
            image.tags.forEach { TagView(tag = it) }
        }
    }
}