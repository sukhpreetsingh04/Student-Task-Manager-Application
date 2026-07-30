package com.application.studenttaskmanager.components.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyTaskState(userName: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello, $userName",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFB74D)
        )

        Text(
            text = "Ready to tackle today's tasks?",
            fontSize = 16.sp,
            color = Color(0xFFFFB74D)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "No tasks yet.\nTap + to add one.",
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFFFFB74D)
        )
    }
}