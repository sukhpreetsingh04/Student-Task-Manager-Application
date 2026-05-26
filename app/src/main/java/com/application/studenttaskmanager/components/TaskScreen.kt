package com.application.studenttaskmanager.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(modifier: Modifier = Modifier, onSubmit: () -> Unit) {

    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()

    val taskScreenBackgroundColor = Color(0xFF121212)

    val myButtonBackgroundColor = remember {
        mutableStateOf(Color(0xFFFF9800))
    }

    val myButtonTextColor = remember {
        mutableStateOf(Color(0xFF121212))
    }

    val myTextFieldColor = TextFieldDefaults.colors(
        focusedContainerColor = Color(0xFF1E1E1E),
        unfocusedContainerColor = Color(0xFF252525),

        focusedTextColor = Color(0xFFFFFFFF),
        unfocusedTextColor = Color(0xFFE0E0E0),

        focusedIndicatorColor = Color(0xFFFF9800),
        unfocusedIndicatorColor = Color(0xFFFFB74D),

        cursorColor = Color(0xFFFF9800),

        focusedLabelColor = Color(0xFFFFB74D),
        unfocusedLabelColor = Color(0xFFBDBDBD),

        focusedPlaceholderColor = Color(0xFFBDBDBD),
        unfocusedPlaceholderColor = Color(0xFF8A8A8A)
    )


    var selectedDate by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(taskScreenBackgroundColor, shape = RoundedCornerShape(12.dp))
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
            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false }, confirmButton = {
                        Button(onClick = {
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
                        Button(onClick = { showDatePicker = false }) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onSubmit() },
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = myButtonBackgroundColor.value,
                contentColor = myButtonTextColor.value
            ),
        ) { Text("Submit", fontSize = 20.sp, fontWeight = FontWeight.Bold) }
    }
}