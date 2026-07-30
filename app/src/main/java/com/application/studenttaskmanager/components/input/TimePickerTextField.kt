package com.application.studenttaskmanager.components.input

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
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.application.studenttaskmanager.data.TaskDraft
import com.application.studenttaskmanager.ui.design.AppTextFieldColors
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerTextField(
    draft: TaskDraft,
    onDraftChange: (TaskDraft) -> Unit
) {

    var showTimePicker by rememberSaveable { mutableStateOf(false) }

    val calendar = Calendar.getInstance().apply {
        draft.dueAtMillis?.let {
            timeInMillis = it
        }
    }

    val timePickerState = rememberTimePickerState(
        initialHour = calendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = calendar.get(Calendar.MINUTE)
    )

    val selectedTime = draft.dueAtMillis?.let {
        SimpleDateFormat("HH:mm", Locale.getDefault())
            .format(Date(it))
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
                        val calendar = Calendar.getInstance().apply {
                            draft.dueAtMillis?.let { timeInMillis = it }

                            set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                            set(Calendar.MINUTE, timePickerState.minute)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }

                        onDraftChange(
                            draft.copy(
                                dueAtMillis = calendar.timeInMillis
                            )
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
        colors = AppTextFieldColors.default(),
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