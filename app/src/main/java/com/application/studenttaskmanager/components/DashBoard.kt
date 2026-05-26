package com.application.studenttaskmanager.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DashBoard(modifier: Modifier = Modifier) {

    TopApplicationBar { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues).background(color = MaterialTheme.colorScheme.background)) {
            TaskCard()
        }
    }

}