package com.application.studenttaskmanager.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.studenttaskmanager.R
import com.application.studenttaskmanager.data.TaskDraft
import com.application.studenttaskmanager.data.TaskItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var showTimePicker by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()
    var expanded by rememberSaveable { mutableStateOf(false) }
    val taskList = stringArrayResource(R.array.taskList)
    val itemPosition = remember(task?.id) {
        mutableStateOf(
            taskList.indexOf(task?.category).takeIf { it >= 0 } ?: 0
        )
    }
    var selectedTime by rememberSaveable { mutableStateOf("") }
    var selectedDate by rememberSaveable { mutableStateOf("") }
    var selectedDateMillis by rememberSaveable { mutableStateOf<Long?>(null) }
    var description by rememberSaveable(task?.id) {
        mutableStateOf(task?.title ?: "")
    }

    val datePickerColors = DatePickerDefaults.colors(
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        headlineContentColor = Color(0xFFFFB74D),
        weekdayContentColor = MaterialTheme.colorScheme.onSurface,
        dayContentColor = MaterialTheme.colorScheme.onSurface,
        selectedDayContainerColor = Color(0xFFFFB74D),
        selectedDayContentColor = Color.White,
        todayDateBorderColor = Color(0xFFFFB74D),
        todayContentColor = Color(0xFFFFB74D),
        navigationContentColor = Color(0xFFFFB74D),
        dividerColor = MaterialTheme.colorScheme.outlineVariant
    )

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

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            colors = datePickerColors,
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFB74D),
                        contentColor = Color.White
                    ),
                    onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            selectedDateMillis = millis
                            selectedDate = SimpleDateFormat(
                                "dd/MM/yyyy",
                                Locale.getDefault()
                            ).format(Date(millis))
                        }
                        showDatePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                Button(
                    onClick = { showDatePicker = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFB74D),
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState, colors = datePickerColors)
        }
    }

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
                    if (description.isBlank()) {
                        Toast.makeText(context, "Please enter a task", Toast.LENGTH_SHORT).show()
                        return@FloatingActionButton
                    }

                    onSubmit(
                        TaskDraft(
                            title = description,
                            category = taskList[itemPosition.value],
                            dueAtMillis = selectedDateMillis?.let { millis ->
                                val utcDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                                    .apply {
                                        timeInMillis = millis
                                    }

                                Calendar.getInstance()
                                    .apply {
                                        set(Calendar.YEAR, utcDate.get(Calendar.YEAR))
                                        set(Calendar.MONTH, utcDate.get(Calendar.MONTH))
                                        set(
                                            Calendar.DAY_OF_MONTH,
                                            utcDate.get(Calendar.DAY_OF_MONTH)
                                        )
                                        set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                        set(Calendar.MINUTE, timePickerState.minute)
                                        set(Calendar.SECOND, 0)
                                        set(Calendar.MILLISECOND, 0)
                                    }
                                    .timeInMillis
                            }
                        )
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
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "What is to be done?",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Enter Task Here") },
                    colors = outLinedTextFieldColors,
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

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Notification",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true }
                ) {
                    OutlinedTextField(
                        value = selectedDate,
                        onValueChange = {},
                        readOnly = true,
                        colors = outLinedTextFieldColors,
                        label = { Text("Not Set") },
                        shape = RoundedCornerShape(12.dp),
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(
                                    imageVector = Icons.Filled.CalendarMonth,
                                    contentDescription = "Select date",
                                    tint = Color(0xFFFFB74D)
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

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

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Task List",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = taskList[itemPosition.value],
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth(),
                        colors = outLinedTextFieldColors,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        taskList.forEachIndexed { index, task ->
                            DropdownMenuItem(
                                text = { Text(task) },
                                onClick = {
                                    itemPosition.value = index
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
