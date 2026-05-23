package com.application.studenttaskmanager.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DashBoard(modifier: Modifier = Modifier) {

    TopApplicationBar { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues).background(color = Color.White)) {
            TaskCard()
        }
    }

}