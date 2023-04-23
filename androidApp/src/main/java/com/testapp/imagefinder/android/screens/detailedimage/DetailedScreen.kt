package com.testapp.imagefinder.android.screens.detailedimage

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.testapp.coreui.AsyncImageLoading
import com.testapp.coreui.TagView
import com.testapp.coreui.navigation.NavigationManager
import com.testapp.entities.Image
import com.testapp.imagefinder.android.R
import com.testapp.imagefinder.android.screens.detailedimage.model.DetailedEvent
import com.testapp.imagefinder.android.screens.detailedimage.model.DetailedViewState

@Composable
fun DetailedScreen(
    navController: NavigationManager,
    viewModel: DetailedViewModel
) {
    LaunchedEffect(Unit) {
        val args = navController.getArgs()
        if (args != null && args is Image) {
            viewModel.obtainEvent(DetailedEvent.PutArgs(args))
        }
    }
    val state = viewModel.viewStates().collectAsState()
    DetailedView(
        state = state.value,
        onClose = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DetailedView(
    state: DetailedViewState,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 10.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.padding(vertical = 10.dp),
                onClick = onClose,
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                border = BorderStroke(width = 1.dp, color = Color.Black)
            ) {
                Image(
                    modifier = Modifier.padding(all = 10.dp),
                    painter = painterResource(id = R.drawable.baseline_clear_24),
                    contentDescription = null
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(size = 20.dp),
                elevation = 5.dp
            ) {
                AsyncImageLoading(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(ratio = state.ratio)
                        .defaultMinSize(minHeight = 200.dp),
                    model = state.image.largeImageURL
                )
            }
            Text(
                text = state.image.user,
                fontSize = 25.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.dp))
            FlowRow(modifier = Modifier.fillMaxWidth()) {
                InfoView(
                    image = R.drawable.baseline_download_24,
                    text = state.image.downloads.toString()
                )
                InfoView(
                    image = R.drawable.baseline_forum_24,
                    text = state.image.comments.toString()
                )
                InfoView(
                    image = R.drawable.baseline_thumb_up_24,
                    text = state.image.likes.toString()
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            FlowRow(modifier = Modifier.fillMaxWidth()) {
                state.image.tags.forEach { TagView(tag = it) }
            }
        }
    }
}

@Composable
private fun InfoView(image: Int, text: String) {
    Card(
        modifier = Modifier.padding(all = 2.dp),
        shape = RoundedCornerShape(size = 25.dp),
        backgroundColor = Color.Gray,
        border = BorderStroke(width = 1.dp, color = Color.Black),
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = image), contentDescription = null)
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = text,
                fontSize = 15.sp,
                color = Color.White
            )
        }
    }
}