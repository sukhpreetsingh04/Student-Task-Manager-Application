package com.application.studenttaskmanager.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.application.studenttaskmanager.data.TaskDraft
import com.application.studenttaskmanager.data.TaskItem
import com.application.studenttaskmanager.data.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DashBoard(
    tasks: List<TaskItem>,
    user: User,
    onSubmitTask: (TaskDraft) -> Unit,
    onDeleteTask: (TaskItem) -> Unit,
    onToggleTask: (TaskItem) -> Unit,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showTaskDialog by rememberSaveable { mutableStateOf(false) }
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
                        onDeleteTask = onDeleteTask
                    )
                }
            }

            if (activeTasks.isEmpty()) {
                EmptyTaskState(userName = user.name)
            }

            TaskCard(onClick = { showTaskDialog = true })

            if (showTaskDialog) {
                Dialog(onDismissRequest = { showTaskDialog = false }) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    ) {
                        TaskScreen(
                            onSubmit = { newTask ->
                                onSubmitTask(newTask)
                                showTaskDialog = false
                            },
                            onCancel = { showTaskDialog = false }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DashboardSummary(tasks: List<TaskItem>, userName: String) {
    val completed = tasks.count { it.isCompleted }
    val pending = tasks.size - completed

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Hello, $userName",
                color = Color(0xFFFFB74D),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$pending pending - $completed completed",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun TaskRow(
    task: TaskItem,
    cardColor: Color,
    onToggleTask: (TaskItem) -> Unit,
    onDeleteTask: (TaskItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onToggleTask(task) }) {
                Icon(
                    imageVector = if (task.isCompleted)
                        Icons.Default.CheckCircle
                    else
                        Icons.Outlined.RadioButtonUnchecked,
                    contentDescription = "Complete Task",
                    tint = Color(0xFFFFB74D)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFFFB74D),
                    textDecoration = if (task.isCompleted)
                        TextDecoration.LineThrough
                    else
                        TextDecoration.None
                )
                Text(
                    text = "${task.category}${task.dueAtMillis?.let { " - ${formatDateTime(it)}" } ?: ""}",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = { onDeleteTask(task) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Task",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun EmptyTaskState(userName: String) {
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

fun formatDateTime(millis: Long): String {
    return SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(millis))
}

