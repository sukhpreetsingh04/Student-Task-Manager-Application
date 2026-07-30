package com.application.studenttaskmanager.components.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.application.studenttaskmanager.data.TaskDraft
import com.application.studenttaskmanager.ui.Design.AppTextFieldColors

@Composable
fun DescriptionTextField(
    draft: TaskDraft,
    onDraftChange: (TaskDraft) -> Unit
) {

    OutlinedTextField(
        value = draft.title,
        onValueChange = {
            onDraftChange(
                draft.copy(title = it)
            )
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Enter Task Here") },
        colors = AppTextFieldColors.default(),
        shape = RoundedCornerShape(12.dp),
        trailingIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Mic,
                    contentDescription = "Voice input",
                    tint = Color(0xFFFFB74D)
                )
            }
        }
    )
}