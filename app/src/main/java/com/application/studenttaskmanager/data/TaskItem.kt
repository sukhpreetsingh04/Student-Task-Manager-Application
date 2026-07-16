package com.application.studenttaskmanager.data

data class TaskItem(
    val id: Long = 0,
    val userId: Long,
    val title: String,
    val category: String,
    val dueAtMillis: Long?,
    val isCompleted: Boolean = false,
    val createdAtMillis: Long = System.currentTimeMillis(),
    val completedAtMillis: Long? = null
)

