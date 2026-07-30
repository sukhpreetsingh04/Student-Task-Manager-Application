package com.application.studenttaskmanager.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.core.content.edit

class UserRepository(context: Context) {
    private val appContext = context.applicationContext
    private val dbHelper = StudentDatabaseHelper(appContext)
    private val preferences =
        appContext.getSharedPreferences("student_task_manager_session", Context.MODE_PRIVATE)

    fun currentUser(): User? {
        val userId = preferences.getLong(KEY_USER_ID, 0L)
        return if (userId > 0L) getUserById(userId) else null
    }


    fun register(name: String, email: String, password: String): Result<User> {
        val cleanName = name.trim().ifBlank { "Student" }
        val cleanEmail = email.trim().lowercase()

        if (!cleanEmail.contains("@") || password.length < 6) {
            return Result.failure(
                IllegalArgumentException("Use a valid email and a password with at least 6 characters")
            )
        }

        return try {
            val values = ContentValues().apply {
                put("name", cleanName)
                put("email", cleanEmail)
                put("password_hash", RepositoryUtils.passwordHash(cleanEmail, password))
                put("created_at", System.currentTimeMillis())
            }

            val id = dbHelper.writableDatabase.insertOrThrow("users", null, values)
            val user = User(id = id, name = cleanName, email = cleanEmail)
            saveSession(user)
            Result.success(user)
        } catch (_: SQLiteConstraintException) {
            Result.failure(IllegalArgumentException("This email is already registered"))
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    fun login(email: String, password: String): Result<User> {
        val cleanEmail = email.trim().lowercase()
        val expectedHash = RepositoryUtils.passwordHash(cleanEmail, password)
        val db = dbHelper.readableDatabase

        db.rawQuery(
            "SELECT id, name, email FROM users WHERE email = ? AND password_hash = ?",
            arrayOf(cleanEmail, expectedHash)
        ).use { cursor ->
            if (!cursor.moveToFirst()) {
                return Result.failure(IllegalArgumentException("Invalid email or password"))
            }

            val user = User(
                id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            )
            saveSession(user)
            return Result.success(user)
        }
    }

    fun logout() {
        preferences.edit { clear() }
    }

    private fun saveSession(user: User) {
        preferences.edit { putLong(KEY_USER_ID, user.id) }
    }

    private fun getUserById(userId: Long): User? {
        dbHelper.readableDatabase.rawQuery(
            "SELECT id, name, email FROM users WHERE id = ?", arrayOf(userId.toString())
        ).use { cursor ->
            if (!cursor.moveToFirst()) return null
            return User(
                id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            )
        }
    }

    companion object {
        private const val KEY_USER_ID = "user_id"
    }
}
