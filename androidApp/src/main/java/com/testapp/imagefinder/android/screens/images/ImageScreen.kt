package com.testapp.imagefinder.android.screens.images

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.testapp.coreui.Keyboard
import com.testapp.coreui.TagView
import com.testapp.coreui.keyboardAsState
import com.testapp.entities.Image
import com.testapp.imagefinder.android.R
import com.testapp.imagefinder.android.screens.images.model.ImagesEvent
import com.testapp.imagefinder.android.screens.images.model.ImagesViewState

@Composable
fun ImageScreen(navController: NavController, viewModel: ImagesViewModel) {
    val state = viewModel.viewStates().collectAsState()
    ImageView(
        state = state.value,
        onTextChange = { viewModel.obtainEvent(ImagesEvent.OnTextChanged(it)) },
        onLoadNext = { viewModel.obtainEvent(ImagesEvent.OnLoadNext) },
        onHideKeyBoard = { viewModel.obtainEvent(ImagesEvent.OnHideKeyboard) }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ImageView(
    state: ImagesViewState,
    onTextChange: (String) -> Unit,
    onLoadNext: () -> Unit,
    onHideKeyBoard: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val keyboardState by keyboardAsState()
    val lastKeyBoardState = remember { mutableStateOf(Keyboard.Closed) }
    val scrollState = rememberLazyListState()
    val index = remember { derivedStateOf { scrollState.firstVisibleItemIndex } }.value
    if (state.images.isNotEmpty() && index + 20 > state.images.size) {
        onLoadNext.invoke()
    }
    if (keyboardState == Keyboard.Closed) {
        if (lastKeyBoardState.value != keyboardState) {
            onHideKeyBoard.invoke()
        }
    }
    lastKeyBoardState.value = keyboardState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .background(color = Color.White)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            singleLine = true,
            value = state.textSearch,
            onValueChange = onTextChange
        )
        if (keyboardState == Keyboard.Closed) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(80.dp),
                        color = Color.Green,
                        strokeWidth = 10.dp
                    )
                }
            } else if (state.images.isNotEmpty()) {
                LazyColumn(state = scrollState) {
                    items(state.images) {
                        ImageRow(it)
                    }
                }
            } else {
                Text(
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.empty),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 10.dp,
                                vertical = 5.dp
                            ),
                        text = stringResource(id = R.string.saved_search),
                        fontSize = 10.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
                items(state.savedSearchWords) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 10.dp,
                                vertical = 5.dp
                            )
                            .clickable {
                                onTextChange.invoke(it)
                                keyboardController?.hide()
                            },
                        text = it,
                        fontSize = 25.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
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
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image.webFormatURL)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .crossfade(true)
                    .build(),
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