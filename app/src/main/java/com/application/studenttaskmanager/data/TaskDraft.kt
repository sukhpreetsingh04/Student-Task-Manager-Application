package com.application.studenttaskmanager.data

data class TaskDraft(
    val title: String,
    val description: String,
    val category: String,
    val dueAtMillis: Long?
)
