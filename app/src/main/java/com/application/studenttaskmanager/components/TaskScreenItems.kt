package com.application.studenttaskmanager.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.studenttaskmanager.data.TaskDraft

@Composable
fun TaskScreenItems(
    draft: TaskDraft,
    onDraftChange: (TaskDraft) -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "What is to be done?",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )

    Spacer(modifier = Modifier.height(16.dp))

    DescriptionTextField(
        draft = draft,
        onDraftChange = onDraftChange
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "Notification",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )

    Spacer(modifier = Modifier.height(16.dp))

    DatePickerTextField(
        draft = draft,
        onDraftChange = onDraftChange
    )

    Spacer(modifier = Modifier.height(16.dp))

    TimePickerTextField(
        draft = draft,
        onDraftChange = onDraftChange
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "Task List",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )

    Spacer(modifier = Modifier.height(16.dp))

    ExposedDropDownMenu(
        draft = draft,
        onDraftChange = onDraftChange
    )
}