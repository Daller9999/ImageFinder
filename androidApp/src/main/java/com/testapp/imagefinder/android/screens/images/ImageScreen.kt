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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.testapp.coreui.Keyboard
import com.testapp.coreui.TagView
import com.testapp.coreui.keyboardAsState
import com.testapp.coreui.navigation.NavigationManager
import com.testapp.entities.Image
import com.testapp.imagefinder.android.R
import com.testapp.imagefinder.android.Screens
import com.testapp.imagefinder.android.screens.images.model.ImagesEvent
import com.testapp.imagefinder.android.screens.images.model.ImagesViewState

@Composable
fun ImageScreen(navController: NavigationManager, viewModel: ImagesViewModel) {
    val state = viewModel.viewStates().collectAsState()
    ImageView(
        state = state.value,
        onTextChange = { viewModel.obtainEvent(ImagesEvent.OnTextChanged(it)) },
        onLoadNext = { viewModel.obtainEvent(ImagesEvent.OnLoadNext) },
        onHideKeyBoard = { viewModel.obtainEvent(ImagesEvent.OnHideKeyboard) },
        onImageClick = {
            navController.navigate(
                route = Screens.DETAILED.name,
                bundle = bundleOf()
            )
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ImageView(
    state: ImagesViewState,
    onTextChange: (String) -> Unit,
    onLoadNext: () -> Unit,
    onHideKeyBoard: () -> Unit,
    onImageClick: (Image) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val keyboardState by keyboardAsState()
    val lastKeyBoardState = remember { mutableStateOf(Keyboard.Closed) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    if (keyboardState == Keyboard.Closed) {
        if (lastKeyBoardState.value != keyboardState) {
            onHideKeyBoard.invoke()
            focusManager.clearFocus()
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
                .padding(vertical = 10.dp)
                .focusRequester(focusRequester),
            singleLine = true,
            value = state.textSearch,
            onValueChange = onTextChange
        )
        if (keyboardState == Keyboard.Closed) {
            ImagesList(
                state = state,
                onLoadNext = onLoadNext,
                onImageClick = onImageClick
            )
        } else {
            SavedSearch(
                state = state,
                onClick = {
                    onTextChange.invoke(it)
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            )
        }
    }
}

@Composable
private fun ImagesList(
    state: ImagesViewState,
    onLoadNext: () -> Unit,
    onImageClick: (Image) -> Unit
) {
    val scrollState = rememberLazyListState()
    val index = remember { derivedStateOf { scrollState.firstVisibleItemIndex } }.value
    if (state.images.isNotEmpty() && index + 20 > state.images.size) {
        onLoadNext.invoke()
    }

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
                ImageRow(
                    images = it,
                    onImageClick = onImageClick
                )
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
}

@Composable
private fun SavedSearch(
    state: ImagesViewState,
    onClick: (String) -> Unit
) {
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
                        onClick(it)
                    },
                text = it,
                fontSize = 25.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ImageRow(images: List<Image>, onImageClick: (Image) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (element in images) {
            ImageViewInfo(
                modifier = Modifier.weight(1f),
                image = element,
                onImageClick = onImageClick
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ImageViewInfo(
    modifier: Modifier,
    image: Image,
    onImageClick: (Image) -> Unit
) {
    Column(modifier = modifier
        .padding(all = 2.dp)
        .clickable { onImageClick.invoke(image) }
    ) {
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