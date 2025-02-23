package retro99.games.api

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.retro99.base.now
import com.retro99.games.implementation.GamesRepository
import com.retro99.games.implementation.model.GameDomainModel
import com.retro99.paging.domain.BardlyRemoteMediator
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject
import retro99.games.api.local.GamesLocalDataSource
import retro99.games.api.local.model.toDomainModel
import retro99.games.api.local.model.toEntity
import retro99.games.api.remote.GamesRemoteDataSource
import retro99.games.api.remote.model.toDomainModel
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class GamesDataRepository(
    private val remoteSource: GamesRemoteDataSource,
    private val localSource: GamesLocalDataSource,
) : GamesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getGames(query: String?): Flow<PagingData<GameDomainModel>> {
        val remotePagingSource = remoteSource.getGames(query)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            remoteMediator = BardlyRemoteMediator(
                remoteSource = remotePagingSource,
                localSource = localSource.getGames(query),
                saveToLocal = { localSource.saveGames(it) },
                remoteToLocal = { it.toDomainModel().toEntity() }
            ),
            pagingSourceFactory = { localSource.getGames(query) }
        ).flow.toDomainModel()
    }

    override suspend fun getRecentlyOpenGames(amount: Int): Result<List<GameDomainModel>> {
        return localSource.getRecentlyOpenGames(amount).map { it.toDomainModel() }
    }

    override suspend fun getGamesById(ids: List<Int>): Result<List<GameDomainModel>> {
        return localSource.getGamesById(ids).map { it.toDomainModel() }
    }

    override suspend fun updateGameOpenDate(gameId: Int): Result<Unit> {
        return localSource.updateGameOpenTime(gameId, now())
    }
}