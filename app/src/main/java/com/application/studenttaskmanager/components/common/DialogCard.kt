package com.application.studenttaskmanager.components.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.application.studenttaskmanager.data.TaskDraft
import com.application.studenttaskmanager.data.TaskItem
import com.application.studenttaskmanager.screens.TaskScreen

@Composable
fun DialogCard(
    task: TaskItem?,
    onSubmitTask: (TaskDraft) -> Unit,
    onUpdateTask: (Long, TaskDraft) -> Unit,
    onDismiss: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
            Card {
                TaskScreen(
                    task = task,
                    onSubmit = { draft ->
                        task?.let {
                            onUpdateTask(it.id, draft)
                        } ?: onSubmitTask(draft)

                        onDismiss()
                    },
                    onCancel = onDismiss
                )
            }
    }
}