package com.retro99.database.api

interface DatabaseExecutor {
    suspend fun <T> executeDatabaseOperation(operation: suspend () -> T): Result<T>
}