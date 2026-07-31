package com.application.studenttaskmanager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.application.studenttaskmanager.components.task.DashboardSummary
import com.application.studenttaskmanager.components.common.DialogCard
import com.application.studenttaskmanager.components.task.EmptyTaskState
import com.application.studenttaskmanager.components.task.TaskRow
import com.application.studenttaskmanager.data.TaskItem
import com.application.studenttaskmanager.data.User
import com.application.studenttaskmanager.ui.theme.StudentTaskManagerTheme
import com.application.studenttaskmanager.components.common.TopApplicationBar
import com.application.studenttaskmanager.components.task.TaskCard
import com.application.studenttaskmanager.data.TaskDraft

@Composable
fun DashBoard(
    tasks: List<TaskItem>,
    user: User,
    onUpdateTask: (Long, TaskDraft) -> Unit,
    onSubmitTask: (TaskDraft) -> Unit,
    onDeleteTask: (TaskItem) -> Unit,
    onToggleTask: (TaskItem) -> Unit,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showTaskDialog by rememberSaveable { mutableStateOf(false) }
    var editingTask by rememberSaveable { mutableStateOf<TaskItem?>(null) }
    val lightCardColor = Color.White
    val darkCardColor = Color(0xFF2A2A2A)
    val activeTasks = tasks.filterNot { it.isCompleted }

    TopApplicationBar(
        onMenuItemSelected = { item ->
            when (item) {
                "All Tasks" -> onNavigate("DashBoard")
                "Logout" -> onLogout()
            }
        },
        onMoreItemSelected = { item ->
            when (item) {
                "DeadLines" -> onNavigate("DeadLineScreen")
                "Your Progress" -> onNavigate("CompletedTasksScreen")
                "Analytics and Weekly Reports" -> onNavigate("AnalyticsScreen")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                item {
                    DashboardSummary(tasks = tasks, userName = user.name)
                }

                items(activeTasks, key = { it.id }) { task ->
                    TaskRow(
                        task = task,
                        cardColor = if (isSystemInDarkTheme()) darkCardColor else lightCardColor,
                        onToggleTask = onToggleTask,
                        onEditTask = { task ->
                            editingTask = task
                            showTaskDialog = true
                        },
                        onDeleteTask = onDeleteTask
                    )
                }
            }

            if (activeTasks.isEmpty()) {
                EmptyTaskState(userName = user.name)
            }

            TaskCard(onClick = {
                showTaskDialog = true
            })

            if (showTaskDialog) {
                Dialog(onDismissRequest = { showTaskDialog = false }) {
                    DialogCard(
                        task = editingTask,
                        onSubmitTask = { draft ->
                            onSubmitTask(draft)
                            showTaskDialog = false
                            editingTask = null
                        },
                        onUpdateTask = { id, draft ->
                            onUpdateTask(id, draft)
                            showTaskDialog = false
                            editingTask = null
                        },
                        onDismiss = {
                            showTaskDialog = false
                            editingTask = null
                        }
                    )
                }
            }
        }
    }
}
