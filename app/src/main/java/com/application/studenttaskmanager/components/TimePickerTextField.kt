package com.application.studenttaskmanager.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.application.studenttaskmanager.data.TaskDraft
import com.application.studenttaskmanager.data.TaskItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerTextField(
    task: TaskItem? = null, draft: TaskDraft,
    onDraftChange: (TaskDraft) -> Unit
) {

    val outLinedTextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        focusedIndicatorColor = Color(0xFFFFB74D),
        unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
        cursorColor = Color(0xFFFFB74D),
        focusedLabelColor = Color(0xFFFFB74D),
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
    )

    var showTimePicker by rememberSaveable { mutableStateOf(false) }

    val calendar = remember(task?.id) {
        Calendar.getInstance().apply {
            task?.dueAtMillis?.let {
                timeInMillis = it
            }
        }
    }

    val timePickerState = rememberTimePickerState(
        initialHour = calendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = calendar.get(Calendar.MINUTE)
    )

    var selectedTime = draft.dueAtMillis?.let {
        SimpleDateFormat(
            "HH:mm",
            Locale.getDefault()
        ).format(Date(it))
    } ?: ""

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFB74D),
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick = {
                        selectedTime = String.format(
                            "%02d:%02d",
                            timePickerState.hour,
                            timePickerState.minute
                        )
                        showTimePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFB74D),
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick = { showTimePicker = false }
                ) { Text("Cancel") }
            },
            text = {
                TimePicker(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        selectorColor = Color(0xFFFFB74D),
                        clockDialColor = if (isSystemInDarkTheme())
                            Color(0xFF3A3A40)
                        else
                            Color(0xFFF3F3F7),
                        timeSelectorSelectedContainerColor = Color(0xFFFFB74D),
                        timeSelectorSelectedContentColor = Color.White
                    )
                )
            }
        )
    }

    OutlinedTextField(
        value = selectedTime,
        onValueChange = {},
        readOnly = true,
        colors = outLinedTextFieldColors,
        label = { Text("Not Set") },
        shape = RoundedCornerShape(12.dp),
        trailingIcon = {
            IconButton(onClick = { showTimePicker = true }) {
                Icon(
                    imageVector = Icons.Filled.Alarm,
                    contentDescription = "Select time",
                    tint = Color(0xFFFFB74D)
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showTimePicker = true }
    )
}