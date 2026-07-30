package com.application.studenttaskmanager.data.mapper

import com.application.studenttaskmanager.data.TaskItem

fun android.database.Cursor.toTaskItem(): TaskItem {
    val dueColumn = getColumnIndexOrThrow("due_at")
    val completedColumn = getColumnIndexOrThrow("completed_at")
    return TaskItem(
        id = getLong(getColumnIndexOrThrow("id")),
        userId = getLong(getColumnIndexOrThrow("user_id")),
        title = getString(getColumnIndexOrThrow("title")),
        description = getString(getColumnIndexOrThrow("description")),
        category = getString(getColumnIndexOrThrow("category")),
        dueAtMillis = if (isNull(dueColumn)) null else getLong(dueColumn),
        isCompleted = getInt(getColumnIndexOrThrow("is_completed")) == 1,
        createdAtMillis = getLong(getColumnIndexOrThrow("created_at")),
        completedAtMillis = if (isNull(completedColumn)) null
        else getLong(completedColumn)
    )
}