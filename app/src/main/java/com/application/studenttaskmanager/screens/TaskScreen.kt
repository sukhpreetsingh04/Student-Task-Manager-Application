package com.application.studenttaskmanager.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.application.studenttaskmanager.components.task.TaskScreenItems
import com.application.studenttaskmanager.data.TaskDraft
import com.application.studenttaskmanager.data.TaskItem

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    task: TaskItem? = null,
    onSubmit: (TaskDraft) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current

    var draft by remember(task?.id) {
        mutableStateOf(
            TaskDraft(
                title = task?.title ?: "",
                description = task?.description ?: "",
                category = task?.category ?: "",
                dueAtMillis = task?.dueAtMillis
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (task == null) "New Task" else "Edit Task",
                        color = Color(0xFFFFB74D)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = Color(0xFFFFB74D),
                    actionIconContentColor = Color(0xFFFFB74D),
                    navigationIconContentColor = Color(0xFFFFB74D)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (draft.title.isBlank()) {
                        Toast.makeText(
                            context,
                            "Please enter a task",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@FloatingActionButton
                    }

                    onSubmit(
                        draft
                    )
                }
            ) {
                Icon(
                    modifier = Modifier.size(74.dp),
                    imageVector = Icons.Default.CheckBox,
                    contentDescription = "Add Task",
                    tint = Color(0xFFFFB74D)
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            item {
                TaskScreenItems(
                    draft = draft,
                    onDraftChange = { draft = it }
                )
            }
        }
    }
}
