package com.application.studenttaskmanager.components

import com.application.studenttaskmanager.R
import android.annotation.SuppressLint
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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(modifier: Modifier = Modifier, onSubmit: (String) -> Unit) {

    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    var showTimePicker by rememberSaveable { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()

    val timePickerState = rememberTimePickerState()

    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    val taskList = stringArrayResource(R.array.taskList)

    val itemPosition = remember {
        mutableStateOf(0)
    }

    var selectedTime by rememberSaveable { mutableStateOf("") }

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

    var selectedDate by rememberSaveable {
        mutableStateOf("")
    }

    var description by rememberSaveable {
        mutableStateOf("")
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            colors = datePickerColors,
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFB74D),
                        contentColor = Color(0xFFFFFFFF)
                    ), onClick = {
                        val millis =
                            datePickerState.selectedDateMillis
                        if (millis != null) {

                            val formatter =
                                SimpleDateFormat(
                                    "dd/MM/yyyy",
                                    Locale.getDefault()
                                )

                            selectedDate =
                                formatter.format(Date(millis))
                        }
                        showDatePicker = false
                    }) { Text("OK") }
            },
            dismissButton = {
                Button(
                    onClick = { showDatePicker = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFB74D),
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                colors = datePickerColors
            )
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
                    ), onClick = {
                        selectedTime = String.format(
                            "%02d:%02d",
                            timePickerState.hour,
                            timePickerState.minute
                        )
                        showTimePicker = false
                    }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFB74D),
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ), onClick = { showTimePicker = false }) {
                    Text("Cancel")
                }
            },
            text = {
                TimePicker(
                    state = timePickerState, colors = TimePickerDefaults.colors(
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
                        text = "New Task",
                        color = Color(0xFFFFB74D)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ArrowBack icon"
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
                    onSubmit(
                        "$description\n$selectedDate • $selectedTime\n${taskList[itemPosition.value]}"
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
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Mic,
                                contentDescription = "Text-to-Speech-recognition-icon",
                                tint = Color(0xFFFFB74D)
                            )
                        }
                    },
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
                        .clickable { showDatePicker = true }) {
                    OutlinedTextField(
                        value = selectedDate,
                        onValueChange = {},
                        readOnly = true,
                        colors = outLinedTextFieldColors,
                        label = { Text("Not Set") },
                        shape = RoundedCornerShape(12.dp),

                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    showDatePicker = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.CalendarMonth,
                                    contentDescription = "calendar-icon",
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
                                contentDescription = "time-selection-icon",
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
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {

                    OutlinedTextField(
                        value = taskList[itemPosition.value],
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor(
                                ExposedDropdownMenuAnchorType.PrimaryNotEditable
                            )
                            .fillMaxWidth(),
                        colors = outLinedTextFieldColors,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        taskList.forEachIndexed { index, task ->
                            DropdownMenuItem(
                                text = {
                                    Text(task)
                                },
                                onClick = {
                                    itemPosition.value = index
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

//                Button(
//                    onClick = {
//                        onSubmit(
//                            "$description\n$selectedDate • $selectedTime\n${taskList[itemPosition.value]}"
//                        )
//                        description = ""
//                        selectedDate = ""
//                        selectedTime = ""
//                        taskList[itemPosition.value] = ""
//                    },
//                    modifier = Modifier
//                        .padding(20.dp)
//                        .size(48.dp),
//                    shape = RoundedCornerShape(12.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xFFFFB74D),
//                        contentColor = MaterialTheme.colorScheme.onPrimary
//                    ),
//                ) { Icon(
//                    imageVector = Icons.Default.AddBox,
//                    contentDescription = null,
//                    tint = Color.Black
//                ) }
            }
        }
    }
}