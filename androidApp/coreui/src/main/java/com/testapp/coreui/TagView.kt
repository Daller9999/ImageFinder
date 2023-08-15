package com.testapp.coreui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TagView(tag: String) {
    Card(
        modifier = Modifier.padding(all = 2.dp),
        shape = RoundedCornerShape(size = 25.dp),
        backgroundColor = Color.Gray,
        border = BorderStroke(width = 1.dp, color = Color.Black),
        elevation = 5.dp
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = tag,
                fontSize = 12.sp,
                color = Color.White
            )
        }
    }
}