package com.retro99.database.implementation

interface DatabaseExecutor {
    suspend fun <T> executeDatabaseOperation(operation: suspend () -> T): Result<T>
}