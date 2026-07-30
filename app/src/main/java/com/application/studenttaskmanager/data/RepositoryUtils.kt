package com.application.studenttaskmanager.data

import java.security.MessageDigest

object RepositoryUtils {

    fun passwordHash(email: String, password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest("$email:$password".toByteArray())
        return bytes.joinToString(separator = "") { "%02x".format(it) }
    }
}