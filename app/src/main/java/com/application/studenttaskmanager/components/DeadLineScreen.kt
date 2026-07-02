package com.application.studenttaskmanager.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.application.studenttaskmanager.data.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeadLineScreen(
    tasks: List<TaskItem>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val upcoming = tasks
        .filter { !it.isCompleted && it.dueAtMillis != null }
        .sortedBy { it.dueAtMillis }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Upcoming Deadlines") },
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
                    title = "${upcoming.size} upcoming deadlines",
                    subtitle = "Tasks with selected date and time appear here."
                )
            }

            if (upcoming.isEmpty()) {
                item { EmptyScreenText("No upcoming deadlines.") }
            } else {
                items(upcoming, key = { it.id }) { task ->
                    SimpleTaskCard(task = task)
                }
            }
        }
    }
}

