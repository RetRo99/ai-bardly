package retro99.games.api.remote

import androidx.paging.PagingSource
import com.retro99.paging.domain.BardlyPagingSource
import com.retro99.paging.domain.PagingResult
import me.tatarka.inject.annotations.Inject
import retro99.games.api.NetworkClient
import retro99.games.api.get
import retro99.games.api.remote.model.GameDto
import retro99.games.api.remote.model.GamesListDto
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class NetworkGamesRemoteDataSource(
    private val networkClient: NetworkClient,
) : GamesRemoteDataSource {

    override suspend fun getGames(query: String?): PagingSource<Int, GameDto> {
        return BardlyPagingSource(
            initialKey = 1,
            getItems = { key, _ ->
                val response = getGamesUrl(key, query)
                val prevKey =
                    if (response.currentPage == 0) null else (response.currentPage.dec())
                val nextKey =
                    if (response.currentPage == response.totalPages) null else (response.currentPage.inc())
                PagingResult(
                    response.games,
                    prevKey,
                    nextKey
                )
            }
        )
    }

    private suspend fun getGamesUrl(page: Int, query: String?): GamesListDto {
        return networkClient.get<GamesListDto>(
            path = "games",
            queryBuilder = {
                "page" to page
                "search" to query
            }
        ).getOrThrow()
    }
}
