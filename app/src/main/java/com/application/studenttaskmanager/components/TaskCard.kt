package com.application.studenttaskmanager.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import com.application.studenttaskmanager.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun TaskCard(modifier: Modifier = Modifier) {

    var showTaskScreen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        if (showTaskScreen) {
            TaskScreen(onSubmit = {
                showTaskScreen = false
            })
        }

        if (!showTaskScreen) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add Task",
                tint = Color(0xFFFFB74D),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp)
                    .size(40.dp)
                    .clickable {
                        showTaskScreen = true
                    }
            )
        }
    }
}