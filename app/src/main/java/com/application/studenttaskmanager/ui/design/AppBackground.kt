package com.application.studenttaskmanager.ui.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.application.studenttaskmanager.R


@Composable
fun AppBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .blur(5.dp)
            .paint(
                painter = painterResource(R.drawable.app_background),
                contentScale = ContentScale.Crop
            )
            .background(Color.Black.copy(alpha = 0.35f))
    )
}