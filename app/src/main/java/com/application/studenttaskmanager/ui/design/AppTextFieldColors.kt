package com.application.studenttaskmanager.ui.design

import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppTextFieldColors {

    @Composable
    fun default() = TextFieldDefaults.colors(
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

    @Composable
    fun colors() = DatePickerDefaults.colors(
        titleContentColor = Color.White,
        headlineContentColor = Color.White,
        selectedDayContainerColor = Color(0xFFFFB74D),
        selectedDayContentColor = Color.White,
        todayDateBorderColor = Color(0xFFFFB74D),
        todayContentColor = Color(0xFFFFB74D),
        selectedYearContainerColor = Color(0xFFFFB74D),
        selectedYearContentColor = Color.White,
        currentYearContentColor = Color(0xFFFFB74D),
        navigationContentColor = Color(0xFFFFB74D),
        subheadContentColor = Color(0xFFFFB74D),
        weekdayContentColor = Color.LightGray,
        dividerColor = Color(0xFF666666)
    )

    @Composable
    fun topBarSearch() = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        cursorColor = Color(0xFFFFB74D),
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        focusedLabelColor = Color(0xFFFFB74D),
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
}