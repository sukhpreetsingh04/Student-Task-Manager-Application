package com.application.studenttaskmanager.components.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.studenttaskmanager.util.formatDateTime
import com.application.studenttaskmanager.data.TaskItem
import com.application.studenttaskmanager.data.TaskStatus
import com.application.studenttaskmanager.data.getStatus

@Composable
fun TaskRow(
    task: TaskItem,
    cardColor: Color,
    onToggleTask: (TaskItem) -> Unit,
    onEditTask: (TaskItem) -> Unit,
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

                val (statusText, statusColor) = when (task.getStatus()) {

                    TaskStatus.PENDING ->
                        "Pending" to Color(0xFF4CAF50)

                    TaskStatus.OVERDUE ->
                        "Overdue" to Color(0xFFE53935)

                    TaskStatus.COMPLETED_ON_TIME ->
                        "Completed On Time" to Color(0xFF4CAF50)

                    TaskStatus.COMPLETED_LATE ->
                        "Completed Late" to Color(0xFFFF9800)
                }

                Text(
                    text = "${task.category}${task.dueAtMillis?.let { " • ${formatDateTime(it)}" } ?: ""}",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = statusText,
                    color = statusColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Row {

                IconButton(
                    onClick = { onEditTask(task) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Task",
                        tint = Color(0xFFFFB74D)
                    )
                }

                IconButton(
                    onClick = { onDeleteTask(task) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Task",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}