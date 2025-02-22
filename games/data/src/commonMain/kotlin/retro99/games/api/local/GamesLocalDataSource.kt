package retro99.games.api.local

import androidx.paging.PagingSource
import kotlinx.datetime.LocalDateTime
import retro99.games.api.local.model.GameEntity

interface GamesLocalDataSource {
    fun getGames(query: String?): PagingSource<Int, GameEntity>

    suspend fun getGamesById(ids: List<Int>): Result<List<GameEntity>>

    suspend fun getGame(id: Int): Result<GameEntity>

    suspend fun saveGames(games: List<GameEntity>)

    suspend fun getRecentlyOpenGames(amount: Int): Result<List<GameEntity>>

    suspend fun clearAll()

    suspend fun updateGameOpenTime(id: Int, openedDateTime: LocalDateTime): Result<Unit>
}