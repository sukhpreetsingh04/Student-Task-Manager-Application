package com.application.studenttaskmanager.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember

@Composable
fun Navigation() {
    val tasks = remember { mutableStateListOf<String>() }

    DashBoard(
        tasks = tasks,
        onSubmitTask = { newTask ->
            tasks.add(newTask)
        }
    )
}