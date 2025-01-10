package com.ai.bardly.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GamesRepository(
    private val gamesApi: GamesApi,
    private val gamesStorage: GamesStorage,
) {
    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        gamesStorage.saveObjects(gamesApi.getGames())
    }

    fun getObjects(): Flow<List<GameApiModel>> = gamesStorage.getObjects()
}
