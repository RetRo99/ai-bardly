package com.ai.bardly.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface GamesStorage {
    suspend fun saveObjects(newObjects: List<GameApiModel>)

    fun getObjects(): Flow<List<GameApiModel>>
}

class InMemoryGamesStorage : GamesStorage {
    private val storedObjects = MutableStateFlow(emptyList<GameApiModel>())

    override suspend fun saveObjects(newObjects: List<GameApiModel>) {
        storedObjects.value = newObjects
    }

    override fun getObjects(): Flow<List<GameApiModel>> = storedObjects
}
