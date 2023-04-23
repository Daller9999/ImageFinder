package com.testapp.imagefinder.android.screens.detailedimage

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.testapp.coreui.TagView
import com.testapp.coreui.navigation.NavigationManager
import com.testapp.entities.Image
import com.testapp.imagefinder.android.R
import com.testapp.imagefinder.android.screens.detailedimage.model.DetailedViewState

@Composable
fun DetailedScreen(
    navController: NavigationManager,
    viewModel: DetailedViewModel
) {
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
            .horizontalScroll(rememberScrollState())
            .background(Color.White)
            .padding(horizontal = 10.dp)
    ) {
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onClose,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Image(
                    modifier = Modifier.padding(all = 10.dp),
                    painter = painterResource(id = R.drawable.baseline_clear_24),
                    contentDescription = null
                )
            }
        }
        Card(shape = RoundedCornerShape(size = 20.dp)) {
            AsyncImage(
                modifier = Modifier.height(500.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(state.image.largeImageURL)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null
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

@Composable
private fun InfoView(image: Int, text: String) {
    Card(
        modifier = Modifier.padding(all = 2.dp),
        shape = RoundedCornerShape(size = 25.dp),
        backgroundColor = Color.Gray,
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = image), contentDescription = null)
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = text,
                fontSize = 15.sp,
                color = Color.Black
            )
        }
    }
}