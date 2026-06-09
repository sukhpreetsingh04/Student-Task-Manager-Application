package com.application.studenttaskmanager.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val tasks = rememberSaveable { mutableStateListOf<String>() }
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "UserAuthentication") {
        composable("UserAuthentication") {
            UserAuthentication(navController = navController)
        }
        composable("DashBoard") {
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
    }
}