package com.application.studenttaskmanager.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.application.studenttaskmanager.data.StudentRepository
import com.application.studenttaskmanager.data.TaskItem
import com.application.studenttaskmanager.data.User
import com.application.studenttaskmanager.notifications.TaskNotificationScheduler

@Composable
fun Navigation() {
    val context = LocalContext.current
    val repository = remember { StudentRepository(context) }
    val scheduler = remember { TaskNotificationScheduler(context) }
    val navController = rememberNavController()
    val tasks = remember { mutableStateListOf<TaskItem>() }
    var currentUser by remember { mutableStateOf(repository.currentUser()) }

    fun refreshTasks(user: User?) {
        tasks.clear()
        if (user != null) {
            tasks.addAll(repository.tasksForUser(user.id))
        }
    }

    fun goToDashboard() {
        navController.navigate("DashBoard") {
            popUpTo("UserAuthentication") { inclusive = true }
            launchSingleTop = true
        }
    }

    LaunchedEffect(currentUser?.id) {
        refreshTasks(currentUser)
    }

    NavHost(
        navController = navController,
        startDestination = if (currentUser == null) "UserAuthentication" else "DashBoard"
    ) {
        composable("UserAuthentication") {
            UserAuthentication(
                onLogin = repository::login,
                onRegister = repository::register,
                onAuthenticated = { user ->
                    currentUser = user
                    refreshTasks(user)
                    goToDashboard()
                }
            )
        }

        composable("DashBoard") {
            val user = currentUser
            if (user == null) {
                LaunchedEffect(Unit) {
                    navController.navigate("UserAuthentication") {
                        popUpTo("DashBoard") { inclusive = true }
                    }
                }
            } else {
                DashBoard(
                    tasks = tasks,
                    user = user,
                    onUpdateTask = { taskId, draft ->
                        repository.updateTask(taskId, draft)
                        refreshTasks(user)
                    },
                    onSubmitTask = { draft ->
                        val savedTask = repository.addTask(user.id, draft)
                        scheduler.schedule(savedTask)
                        refreshTasks(user)
                    },
                    onDeleteTask = { task ->
                        scheduler.cancel(task.id)
                        repository.deleteTask(task.id)
                        refreshTasks(user)
                    },
                    onToggleTask = { task ->
                        repository.setTaskCompleted(task.id, !task.isCompleted)
                        if (!task.isCompleted) {
                            scheduler.cancel(task.id)
                        } else {
                            scheduler.schedule(task.copy(isCompleted = false))
                        }
                        refreshTasks(user)
                    },
                    onNavigate = { route ->
                        navController.navigate(route) {
                            launchSingleTop = true
                        }
                    },
                    onLogout = {
                        repository.logout()
                        currentUser = null
                        tasks.clear()
                        navController.navigate("UserAuthentication") {
                            popUpTo("DashBoard") { inclusive = true }
                        }
                    }
                )
            }
        }

        composable("CompletedTasksScreen") {
            CompletedTasksScreen(
                tasks = tasks,
                onBack = { navController.popBackStack() }
            )
        }

        composable("DeadLineScreen") {
            DeadLineScreen(
                tasks = tasks,
                onBack = { navController.popBackStack() }
            )
        }

        composable("AnalyticsScreen") {
            AnalyticsScreen(
                tasks = tasks,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

