package com.application.studenttaskmanager.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    val datePickerState = rememberDatePickerState()

    var showTimePicker by rememberSaveable { mutableStateOf(false) }

    var selectedTime by rememberSaveable { mutableStateOf("") }

    val timePickerState = rememberTimePickerState()

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

    val myTextFieldColor = TextFieldDefaults.colors(
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

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(12.dp))
            .padding(20.dp)
    ) {
        TextField(
            value = description,
            onValueChange = { description = it },
            Modifier.fillMaxWidth(),
            label = { Text("Add Tasks") },
            colors = myTextFieldColor,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true }) {
            TextField(
                value = selectedDate,
                onValueChange = {},
                readOnly = true,
                colors = myTextFieldColor,
                label = { Text("Select Date") },
                shape = RoundedCornerShape(12.dp),

                trailingIcon = {
                    IconButton(
                        onClick = {
                            showDatePicker = true
                        }
                    ) {
                        Text("📅")
                    }
                },

                modifier = Modifier.fillMaxWidth()
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = selectedTime,
            onValueChange = {},
            readOnly = true,
            colors = myTextFieldColor,
            label = { Text("Select Time") },
            shape = RoundedCornerShape(12.dp),
            trailingIcon = {
                IconButton(onClick = { showTimePicker = true }) {
                    Text("⏰")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showTimePicker = true }
        )

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
                            clockDialColor = Color(0xFF3A3A40),
                            timeSelectorSelectedContainerColor = Color(0xFFFFB74D),
                            timeSelectorSelectedContentColor = Color.White
                        )
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onSubmit("$description\n$selectedDate • $selectedTime")
                description = ""
                selectedDate = ""
                selectedTime = ""
            },
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFB74D),
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
        ) { Text("Submit", fontSize = 20.sp, fontWeight = FontWeight.Bold) }
    }
}