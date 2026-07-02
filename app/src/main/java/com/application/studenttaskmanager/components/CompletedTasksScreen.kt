package com.application.studenttaskmanager.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.studenttaskmanager.data.TaskItem

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun CompletedTasksScreen(
    tasks: List<TaskItem>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val completedTasks = tasks.filter { it.isCompleted }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Completed Tasks") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = Color(0xFFFFB74D),
                    navigationIconContentColor = Color(0xFFFFB74D)
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            item {
                SummaryCard(
                    title = "${completedTasks.size} tasks completed",
                    subtitle = "Finished work stays here for progress review."
                )
            }

            if (completedTasks.isEmpty()) {
                item {
                    EmptyScreenText("No completed tasks yet.")
                }
            } else {
                items(completedTasks, key = { it.id }) { task ->
                    SimpleTaskCard(task = task)
                }
            }
        }
    }
}

@Composable
fun SummaryCard(title: String, subtitle: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFB74D))
            Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun SimpleTaskCard(task: TaskItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(task.title, fontWeight = FontWeight.Bold, color = Color(0xFFFFB74D))
            Text(
                text = "${task.category}${task.dueAtMillis?.let { " - ${formatDateTime(it)}" } ?: ""}",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun EmptyScreenText(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(top = 24.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

