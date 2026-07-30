package com.application.studenttaskmanager.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDateTime(millis: Long): String {
    return SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(millis))
}