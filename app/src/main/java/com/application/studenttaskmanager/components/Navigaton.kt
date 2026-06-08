package com.application.studenttaskmanager.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun Navigation() {
    val tasks = rememberSaveable { mutableStateListOf<String>() }

    DashBoard(
        tasks = tasks,
        onSubmitTask = { newTask ->
            tasks.add(newTask)
        },
        onDeleteTask = { task ->
            tasks.remove(task)
        }
    )
}