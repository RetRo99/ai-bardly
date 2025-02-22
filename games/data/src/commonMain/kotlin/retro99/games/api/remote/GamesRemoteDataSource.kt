package retro99.games.api.remote

import androidx.paging.PagingSource
import retro99.games.api.remote.model.GameDto

interface GamesRemoteDataSource {
    suspend fun getGames(query: String?): PagingSource<Int, GameDto>
}