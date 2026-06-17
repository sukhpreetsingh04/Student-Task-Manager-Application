package com.application.studenttaskmanager.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation() {
    val tasks = rememberSaveable { mutableStateListOf<String>() }
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "UserAuthentication") {
        composable("UserAuthentication") {
            UserAuthentication(navController = navController)
        }
        composable(
            route = "DashBoard/{userName}",
            arguments = listOf(
                navArgument("userName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val userName =
                backStackEntry.arguments?.getString("userName") ?: ""

            DashBoard(
                tasks = tasks,
                onSubmitTask = { newTask ->
                    tasks.add(newTask)
                },
                onDeleteTask = { task ->
                    tasks.remove(task)
                },
                navController = navController,
                userName = userName
            )
        }
    }
}